#!/bin/bash

echo "🚀 Deploying Laptop Shop to Railway..."

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "📦 Initializing Git repository..."
    git init
    git add .
    git commit -m "Initial commit - Laptop Shop E-commerce Platform"
    echo "✅ Git repository initialized"
    echo ""
    echo "⚠️  IMPORTANT: Next steps:"
    echo "1. Create a new repository on GitHub"
    echo "2. Add remote: git remote add origin https://github.com/YOUR_USERNAME/laptopshop.git"
    echo "3. Push: git push -u origin main"
    echo ""
    echo "Then go to railway.app and deploy from GitHub"
else
    echo "✅ Git repository already exists"
    echo ""
    echo "📊 Current status:"
    git status
    echo ""
    echo "💡 To deploy:"
    echo "1. Push latest changes: git push origin main"
    echo "2. Go to railway.app"
    echo "3. Click 'New Project' → 'Deploy from GitHub repo'"
    echo "4. Select this repository"
fi

echo ""
echo "📋 Deployment Checklist:"
echo "□ Railway account created (https://railway.app)"
echo "□ MySQL plugin will be added automatically"
echo "□ Admin credentials: admin@admin.com / admin"
echo "□ Products already seeded (12 products with images)"
echo ""
echo "🔧 Railway Configuration:"
echo "□ Builder: Nixpacks (auto-detected)"
echo "□ Port: 8080"
echo "□ Health check: /"
echo ""
echo "🌐 After deployment:"
echo "□ Add MySQL database plugin in Railway"
echo "□ Test all features"
echo "□ Configure custom domain (optional)"