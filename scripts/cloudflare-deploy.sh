#!/bin/bash

echo "☁️  Cloudflare Deployment Script"
echo "================================="
echo ""

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 18+ first."
    echo "📖 Download from: https://nodejs.org/"
    exit 1
fi

# Check if npm is installed
if ! command -v npm &> /dev/null; then
    echo "❌ npm is not installed."
    exit 1
fi

echo "✅ Node.js found: $(node --version)"

# Install Wrangler CLI
echo ""
echo "📦 Installing Cloudflare Wrangler CLI..."
npm install -g wrangler

# Login to Cloudflare
echo ""
echo "🔐 Please login to Cloudflare (browser will open)..."
wrangler login

# Create D1 database
echo ""
echo "🗄️  Creating D1 database..."
DB_OUTPUT=$(wrangler d1 create laptopshop-db 2>&1)
DB_ID=$(echo "$DB_OUTPUT" | grep -o 'database_id = "[^"]*"' | cut -d'"' -f2)

if [ -z "$DB_ID" ]; then
    echo "❌ Failed to create D1 database. It might already exist."
    echo "Please check your Cloudflare dashboard for existing database."
    echo "Then update the database_id in cloudflare/worker/wrangler.toml"
else
    echo "✅ D1 database created with ID: $DB_ID"

    # Update wrangler.toml with database ID
    sed -i "s/database_id = \"\"/database_id = \"$DB_ID\"/" cloudflare/worker/wrangler.toml
    echo "✅ Updated wrangler.toml with database ID"
fi

# Initialize database schema
echo ""
echo "📋 Initializing database schema..."
cd cloudflare/database
wrangler d1 execute laptopshop-db --file=schema.sql --remote
cd ../..

# Create KV namespace for sessions
echo ""
echo "🔑 Creating KV namespace for sessions..."
KV_OUTPUT=$(wrangler kv:namespace create "SESSIONS" 2>&1)
KV_ID=$(echo "$KV_OUTPUT" | grep -o 'id = "[^"]*"' | cut -d'"' -f2)

if [ -z "$KV_ID" ]; then
    echo "⚠️  KV namespace might already exist. Please check and update manually."
else
    echo "✅ KV namespace created with ID: $KV_ID"
    sed -i "s/id = \"sessions-kv-namespace-id\"/id = \"$KV_ID\"/" cloudflare/worker/wrangler.toml
    echo "✅ Updated wrangler.toml with KV namespace ID"
fi

# Create R2 bucket for uploads
echo ""
echo "📁 Creating R2 bucket for uploads..."
wrangler r2 bucket create laptopshop-uploads

# Install worker dependencies
echo ""
echo "📦 Installing worker dependencies..."
cd cloudflare/worker
npm install

# Deploy worker
echo ""
echo "🚀 Deploying Cloudflare Worker..."
wrangler deploy

# Get worker URL
WORKER_URL=$(wrangler whoami 2>&1 | grep -o 'https://[^ ]*workers.dev' | head -1)
echo ""
echo "✅ Worker deployed successfully!"
if [ ! -z "$WORKER_URL" ]; then
    echo "🌐 Your API is available at: $WORKER_URL"
fi

# Test the API
echo ""
echo "🧪 Testing API endpoints..."
echo "Fetching products..."
curl -s "$WORKER_URL/api/products" | head -c 200
echo ""
echo ""

cd ../..

# Generate API documentation
echo ""
echo "📚 API Documentation"
echo "==================="
echo ""
echo "Your Cloudflare Worker API is deployed and ready!"
echo ""
echo "Base URL: $WORKER_URL"
echo ""
echo "Available endpoints:"
echo "  GET  $WORKER_URL/api/products           - Get all products"
echo "  GET  $WORKER_URL/api/products/:id       - Get product by ID"
echo "  POST $WORKER_URL/api/register           - User registration"
echo "  POST $WORKER_URL/api/login              - User login"
echo "  POST $WORKER_URL/api/cart/add           - Add to cart"
echo "  GET  $WORKER_URL/api/cart/:userId       - Get cart items"
echo "  POST $WORKER_URL/api/orders             - Create order"
echo ""
echo "Database: Cloudflare D1"
echo "Storage: Cloudflare R2"
echo "Sessions: Cloudflare KV"
echo ""
echo "🎉 Deployment complete!"
echo ""
echo "Next steps:"
echo "1. Update your frontend to use: $WORKER_URL"
echo "2. Configure custom domains in Cloudflare dashboard"
echo "3. Set up analytics and monitoring"