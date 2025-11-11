# ☁️ Cloudflare Deployment Guide - Laptop Shop

Deploy your Spring Boot e-commerce platform using Cloudflare's free tier.

## 🌟 Cloudflare Free Tier Benefits
- **Cloudflare Pages**: Unlimited static hosting
- **Cloudflare Workers**: 100,000 requests/day free
- **Cloudflare D1**: 5GB storage, 25M reads/day free
- **Cloudflare R2**: 10GB storage free
- **Global CDN**: Fast delivery worldwide
- **Automatic HTTPS**: Free SSL certificates

## 🏗️ Deployment Architecture

### Option 1: Static Pages + Workers API (Recommended)
```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────┐
│  Cloudflare     │    │  Cloudflare      │    │ Cloudflare  │
│     Pages       │───▶│     Workers      │───▶│     D1      │
│  (Frontend)     │    │   (Backend)      │    │ (Database)  │
└─────────────────┘    └──────────────────┘    └─────────────┘
```

### Option 2: Full Docker on External VM
```
┌─────────────────┐    ┌──────────────────┐
│  Cloudflare     │    │   External VM    │
│    Tunnel       │───▶│ (Docker Container)│
│   (Proxy)       │    │  (Spring Boot)   │
└─────────────────┘    └──────────────────┘
```

---

## 🎯 Option 1: Cloudflare Pages + Workers (Fully Free)

### Step 1: Convert JSP to Static HTML
First, we need to convert the JSP files to static HTML:

```bash
# Create static HTML from JSP
mkdir -p cloudflare-pages
cp -r src/main/webapp/resources cloudflare-pages/
```

### Step 2: Set up Cloudflare D1 Database
```bash
# Install Wrangler CLI
npm install -g wrangler

# Login to Cloudflare
wrangler login

# Create D1 database
wrangler d1 create laptopshop-db

# Note the database ID from output
```

### Step 3: Create Database Schema
Create `cloudflare/schema.sql`:
```sql
-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    full_name TEXT,
    phone TEXT,
    address TEXT,
    role TEXT DEFAULT 'USER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image_url TEXT,
    stock INTEGER DEFAULT 0,
    category TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    total_amount DECIMAL(10,2) NOT NULL,
    status TEXT DEFAULT 'PENDING',
    shipping_address TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order items table
CREATE TABLE IF NOT EXISTS order_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER,
    product_id INTEGER,
    quantity INTEGER NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Cart table
CREATE TABLE IF NOT EXISTS cart (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Cart items table
CREATE TABLE IF NOT EXISTS cart_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cart_id INTEGER,
    product_id INTEGER,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### Step 4: Deploy Database Schema
```bash
# Apply schema to D1 database
wrangler d1 execute laptopshop-db --file=cloudflare/schema.sql
```

### Step 5: Create Cloudflare Worker
Create `cloudflare/worker/src/index.js`:
```javascript
// Cloudflare Worker for Laptop Shop API
import { Router } from 'itty-router';

const router = Router();

// CORS headers
const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  'Access-Control-Allow-Headers': 'Content-Type, Authorization',
};

// Handle CORS preflight
router.options('*', () => new Response(null, { headers: corsHeaders }));

// Get all products
router.get('/api/products', async (request, env) => {
  const { results } = await env.DB.prepare('SELECT * FROM products ORDER BY created_at DESC').all();
  return new Response(JSON.stringify(results), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// Get product by ID
router.get('/api/products/:id', async (request, env) => {
  const id = request.params.id;
  const product = await env.DB.prepare('SELECT * FROM products WHERE id = ?').bind(id).first();

  if (!product) {
    return new Response(JSON.stringify({ error: 'Product not found' }), {
      status: 404,
      headers: { 'Content-Type': 'application/json', ...corsHeaders }
    });
  }

  return new Response(JSON.stringify(product), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// User registration
router.post('/api/register', async (request, env) => {
  const { email, password, fullName } = await request.json();

  // Check if user exists
  const existingUser = await env.DB.prepare('SELECT id FROM users WHERE email = ?').bind(email).first();
  if (existingUser) {
    return new Response(JSON.stringify({ error: 'User already exists' }), {
      status: 400,
      headers: { 'Content-Type': 'application/json', ...corsHeaders }
    });
  }

  // Hash password (simple for demo, use bcrypt in production)
  const hashedPassword = password; // Implement proper hashing

  const result = await env.DB.prepare(
    'INSERT INTO users (email, password, full_name) VALUES (?, ?, ?)'
  ).bind(email, hashedPassword, fullName).run();

  return new Response(JSON.stringify({ id: result.meta.last_row_id, message: 'User created' }), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// User login
router.post('/api/login', async (request, env) => {
  const { email, password } = await request.json();

  const user = await env.DB.prepare(
    'SELECT * FROM users WHERE email = ? AND password = ?'
  ).bind(email, password).first();

  if (!user) {
    return new Response(JSON.stringify({ error: 'Invalid credentials' }), {
      status: 401,
      headers: { 'Content-Type': 'application/json', ...corsHeaders }
    });
  }

  return new Response(JSON.stringify({
    id: user.id,
    email: user.email,
    fullName: user.full_name,
    role: user.role
  }), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// Add to cart
router.post('/api/cart/add', async (request, env) => {
  const { userId, productId, quantity } = await request.json();

  // Get or create cart
  let cart = await env.DB.prepare('SELECT id FROM cart WHERE user_id = ?').bind(userId).first();
  if (!cart) {
    const result = await env.DB.prepare('INSERT INTO cart (user_id) VALUES (?)').bind(userId).run();
    cart = { id: result.meta.last_row_id };
  }

  // Add item to cart
  await env.DB.prepare(
    'INSERT OR REPLACE INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)'
  ).bind(cart.id, productId, quantity).run();

  return new Response(JSON.stringify({ message: 'Added to cart' }), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// Get cart items
router.get('/api/cart/:userId', async (request, env) => {
  const userId = request.params.userId;

  const items = await env.DB.prepare(`
    SELECT ci.*, p.name, p.price, p.image_url
    FROM cart_items ci
    JOIN cart c ON ci.cart_id = c.id
    JOIN products p ON ci.product_id = p.id
    WHERE c.user_id = ?
  `).bind(userId).all();

  return new Response(JSON.stringify(items.results), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// Create order
router.post('/api/orders', async (request, env) => {
  const { userId, items, shippingAddress } = await request.json();

  // Calculate total
  const total = items.reduce((sum, item) => sum + (item.price * item.quantity), 0);

  // Create order
  const orderResult = await env.DB.prepare(
    'INSERT INTO orders (user_id, total_amount, shipping_address) VALUES (?, ?, ?)'
  ).bind(userId, total, shippingAddress).run();
  const orderId = orderResult.meta.last_row_id;

  // Add order items
  for (const item of items) {
    await env.DB.prepare(
      'INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)'
    ).bind(orderId, item.id, item.quantity, item.price).run();
  }

  // Clear cart
  const cart = await env.DB.prepare('SELECT id FROM cart WHERE user_id = ?').bind(userId).first();
  if (cart) {
    await env.DB.prepare('DELETE FROM cart_items WHERE cart_id = ?').bind(cart.id).run();
  }

  return new Response(JSON.stringify({ orderId, message: 'Order created' }), {
    headers: { 'Content-Type': 'application/json', ...corsHeaders }
  });
});

// 404 handler
router.all('*', () => new Response('Not Found', { status: 404 }));

// Export the worker
export default {
  async fetch(request, env, ctx) {
    return router.handle(request, env, ctx);
  },
};
```

### Step 6: Create Wrangler Configuration
Create `cloudflare/worker/wrangler.toml`:
```toml
name = "laptopshop-api"
main = "src/index.js"
compatibility_date = "2024-01-01"

[[d1_databases]]
binding = "DB"
database_name = "laptopshop-db"
database_id = "your-database-id-here"

[vars]
ENVIRONMENT = "production"
```

### Step 7: Deploy Worker
```bash
cd cloudflare/worker
npm init -y
npm install itty-router
wrangler deploy
```

---

## 🎯 Option 2: Cloudflare Tunnel (Easier with Docker)

### Step 1: Prepare Docker Image
Use the existing Dockerfile.

### Step 2: Set up Cloudflare Tunnel
```bash
# Install cloudflared
wget -q https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64.deb
sudo dpkg -i cloudflared-linux-amd64.deb

# Login to Cloudflare
cloudflared tunnel login

# Create tunnel
cloudflared tunnel create laptopshop

# Note the tunnel UUID from output

# Create config file
mkdir -p ~/.cloudflared/
cat > ~/.cloudflared/config.yml << EOF
tunnel: your-tunnel-uuid-here
credentials-file: ~/.cloudflared/your-tunnel-uuid-here.json

ingress:
  - hostname: your-domain.com
    service: http://localhost:8080
  - service: http_status:404
EOF

# Create DNS record
cloudflared tunnel route dns laptopshop your-domain.com
```

### Step 3: Run with Tunnel
```bash
# Start your Docker container
docker-compose up -d

# Start tunnel
cloudflared tunnel run laptopshop
```

---

## 🎯 Option 3: Cloudflare Pages with External API

### Step 1: Create Static Frontend
Convert JSP to static HTML/CSS/JS.

### Step 2: Deploy to Pages
```bash
# Install Wrangler
npm install -g wrangler

# Create Pages project
wrangler pages project create laptopshop

# Deploy
cd cloudflare-pages
wrangler pages deploy
```

### Step 3: Configure External API
Update your frontend JavaScript to call external API.

---

## 🚀 Quick Deploy (Recommended)

### Using Cloudflare Workers + D1 (Fully Free)

```bash
# 1. Install tools
npm install -g wrangler

# 2. Login to Cloudflare
wrangler login

# 3. Clone and setup
git clone https://github.com/kienkien72/Spring-MVC.git
cd Spring-MVC/ProjectRestAPI

# 4. Deploy database
wrangler d1 create laptopshop-db
wrangler d1 execute laptopshop-db --file=cloudflare/schema.sql

# 5. Update worker config with your database ID
# Edit cloudflare/worker/wrangler.toml

# 6. Deploy worker
cd cloudflare/worker
npm install
wrangler deploy

# 7. Get your worker URL
wrangler whoami
```

---

## 💡 Frontend Updates

Update your static HTML to use the Workers API:

```javascript
// API base URL
const API_BASE = 'https://laptopshop-api.your-subdomain.workers.dev';

// Fetch products
async function getProducts() {
  const response = await fetch(`${API_BASE}/api/products`);
  return await response.json();
}

// Login
async function login(email, password) {
  const response = await fetch(`${API_BASE}/api/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  return await response.json();
}
```

---

## 📊 Cost Comparison

| Service | Free Tier | Requests | Storage | Database |
|---------|-----------|----------|---------|----------|
| Workers | 100k/day | ✅ | ❌ | ❌ |
| D1 | 5GB | 25M reads | 5GB | ✅ |
| Pages | Unlimited | ✅ | ✅ | ❌ |
| R2 | 10GB | ✅ | 10GB | ❌ |

---

## 🔧 Environment Variables

Set these in your Worker or in wrangler.toml:

```toml
[vars]
API_URL = "https://your-worker.workers.dev"
CORS_ORIGIN = "*"
JWT_SECRET = "your-secret-key"
UPLOAD_URL = "https://your-r2-account.r2.cloudflarestorage.com"
```

---

## 🎉 Deployment Complete!

Once deployed:
1. **Worker API**: `https://your-worker.subdomain.workers.dev`
2. **Pages Site**: `https://your-pages.pages.dev`
3. **Custom Domain**: Configure in Cloudflare dashboard

### Custom Domain Setup
```bash
# Add custom domain to worker
wrangler custom-domains add api.yourdomain.com

# Add custom domain to pages
wrangler pages custom-domains create yourdomain.com
```

---

## 🆘 Troubleshooting

### Worker Not Responding
```bash
# Check logs
wrangler tail

# Check configuration
wrangler dev
```

### D1 Database Issues
```bash
# Query database directly
wrangler d1 execute laptopshop-db --command="SELECT * FROM users"
```

### CORS Errors
Ensure you include CORS headers in all responses (see worker code above).

---

## 📚 Additional Resources

- [Cloudflare Workers Documentation](https://developers.cloudflare.com/workers/)
- [D1 Database Guide](https://developers.cloudflare.com/d1/)
- [Pages Documentation](https://developers.cloudflare.com/pages/)
- [Tunnel Guide](https://developers.cloudflare.com/cloudflare-one/connections/connect-apps/)

This setup gives you a completely free, globally distributed e-commerce platform!