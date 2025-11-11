#!/bin/bash

echo "☁️  Complete Cloudflare Deployment"
echo "=================================="
echo ""

# Check Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js first."
    echo "📖 See NODE_SETUP.md for installation instructions."
    exit 1
fi

# Check npm
if ! command -v npm &> /dev/null; then
    echo "❌ npm is not installed."
    exit 1
fi

echo "✅ Node.js version: $(node --version)"
echo "✅ npm version: $(npm --version)"
echo ""

# Install Wrangler if not installed
if ! command -v wrangler &> /dev/null; then
    echo "📦 Installing Wrangler CLI..."
    npm install -g wrangler
fi

# Login to Cloudflare
echo "🔐 Logging into Cloudflare..."
wrangler login

# Deploy Worker
echo ""
echo "🚀 Step 1: Deploying Cloudflare Worker (Backend API)..."
cd cloudflare/worker

# Install dependencies
npm install

# Create D1 database if not exists
DB_EXISTS=$(wrangler d1 list 2>&1 | grep -c "laptopshop-db" || true)
if [ "$DB_EXISTS" -eq 0 ]; then
    echo "🗄️ Creating D1 database..."
    DB_OUTPUT=$(wrangler d1 create laptopshop-db)
    DB_ID=$(echo "$DB_OUTPUT" | grep -o 'database_id = "[^"]*"' | cut -d'"' -f2)

    # Update wrangler.toml
    sed -i.bak "s/database_id = \"\"/database_id = \"$DB_ID\"/" wrangler.toml
    rm wrangler.toml.bak
fi

# Initialize database
echo "📋 Initializing database schema..."
wrangler d1 execute laptopshop-db --file=../database/schema.sql --remote

# Deploy worker
echo "🚀 Deploying Worker..."
wrangler deploy

# Get worker URL
WORKER_URL=$(wrangler whoami 2>&1 | grep -o 'https://[^ ]*workers.dev' | head -1)
echo "✅ Worker deployed at: $WORKER_URL"

cd ../..

# Update API URL in frontend
echo ""
echo "📝 Step 2: Updating API URL in frontend..."
node scripts/update-api-url.js "$WORKER_URL"

# Deploy frontend to Pages
echo ""
echo "📄 Step 3: Deploying Frontend to Cloudflare Pages..."
cd cloudflare/pages

# Install dependencies and build
echo "📦 Installing dependencies..."
npm install

echo "📁 Building static files..."
npm run build

# Create a simple _worker.js for Pages Functions if needed
mkdir -p dist/functions
cat > dist/functions/_worker.js << 'EOF'
// Cloudflare Pages Functions for API proxy if needed
export function onRequest(context) {
    // Forward API requests to Worker
    const url = new URL(context.request.url);
    if (url.pathname.startsWith('/api/')) {
        // You can proxy to your Worker here if needed
        return fetch(context.request);
    }

    // Serve static files
    return context.next();
}
EOF

# Deploy to Pages
echo "🚀 Deploying to Cloudflare Pages..."
npx wrangler pages deploy dist --project-name laptopshop

echo ""
echo "🎉 Deployment Complete!"
echo "======================"
echo ""
echo "✅ Backend API: $WORKER_URL"
echo "✅ Frontend: https://laptopshop.pages.dev"
echo ""
echo "📚 What's next:"
echo "1. Test your application"
echo "2. Add custom domain in Cloudflare dashboard"
echo "3. Set up analytics and monitoring"
echo ""
echo "🔗 Quick links:"
echo "- Cloudflare Dashboard: https://dash.cloudflare.com"
echo "- Worker Analytics: https://dash.cloudflare.com/workers"
echo "- Pages Analytics: https://dash.cloudflare.com/pages"