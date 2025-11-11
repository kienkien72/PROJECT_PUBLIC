#!/bin/bash

echo "📦 Installing Wrangler CLI"
echo "========================="

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js first."
    echo "📖 See ../NODE_SETUP.md for instructions."
    exit 1
fi

echo "✅ Node.js found: $(node --version)"

# Method 1: Install via npm (recommended)
echo ""
echo "📦 Installing Wrangler via npm..."
npm install -g wrangler@latest

# Verify installation
if command -v wrangler &> /dev/null; then
    echo "✅ Wrangler installed successfully!"
    echo "📍 Version: $(wrangler --version)"
else
    echo "⚠️  Global installation failed. Using npx instead..."
    echo "   All npm scripts have been updated to use npx wrangler"
fi

# Update package.json to use npx if wrangler not found globally
if ! command -v wrangler &> /dev/null; then
    echo ""
    echo "📝 Updating package.json to use npx..."
    sed -i 's/wrangler pages deploy/npx wrangler pages deploy/g' package.json
    sed -i 's/wrangler pages dev/npx wrangler pages dev/g' package.json
    echo "✅ Updated to use npx"
fi

echo ""
echo "🎉 Installation complete!"
echo ""
echo "Now you can run:"
echo "  npm run deploy"