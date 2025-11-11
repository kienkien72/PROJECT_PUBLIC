#!/bin/bash

echo "🔧 Fixing Cloudflare Pages Configuration"
echo "======================================="
echo ""

# Navigate to pages directory
cd cloudflare/pages

# Install dependencies
echo "📦 Installing dependencies..."
npm install

# Create dist directory and copy files
echo "📁 Building static files..."
npm run build

# Show current structure
echo ""
echo "📂 Current directory structure:"
tree -L 2 || ls -la

echo ""
echo "✅ Configuration fixed!"
echo ""
echo "Now you can deploy with:"
echo "  npm run deploy"
echo ""
echo "Or deploy manually with:"
echo "  npx wrangler pages deploy dist"
echo ""
echo "If you want to preview locally:"
echo "  npm run dev"