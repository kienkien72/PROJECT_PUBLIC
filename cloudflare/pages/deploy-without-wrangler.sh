#!/bin/bash

echo "🚀 Deploying without Wrangler (Manual Upload)"
echo "==========================================="
echo ""

# Create a zip file for manual upload
echo "📦 Creating deployment package..."
cd dist
zip -r ../laptopshop-pages.zip .
cd ..

echo "✅ Created: laptopshop-pages.zip"
echo ""
echo "📋 Next Steps:"
echo "1. Go to Cloudflare Dashboard: https://dash.cloudflare.com"
echo "2. Go to Pages > laptopshop"
echo "3. Click 'Create upload' or 'Upload assets'"
echo "4. Upload the file: laptopshop-pages.zip"
echo ""
echo "🔍 Files included in deployment:"
echo "---"
ls -la dist/
echo ""
echo "📁 Your files are ready at:"
echo "   $(pwd)/laptopshop-pages.zip"