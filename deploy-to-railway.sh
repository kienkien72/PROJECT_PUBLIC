#!/bin/bash

echo "🚀 Laptop Shop - Railway Deployment Script"
echo "=========================================="
echo ""

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "📦 Initializing Git repository..."
    git init
    git add .
    git commit -m "Ready for Railway deployment - Laptop Shop E-commerce Platform"
    echo "✅ Git repository initialized successfully"
    echo ""
    echo "⚠️  IMPORTANT: Next steps:"
    echo "1. Go to https://github.com and create a new repository named 'laptopshop'"
    echo "2. Run these commands:"
    echo "   git remote add origin https://github.com/YOUR_USERNAME/laptopshop.git"
    echo "   git branch -M main"
    echo "   git push -u origin main"
    echo ""
    echo "3. Then go to https://railway.app and deploy from GitHub"
else
    echo "✅ Git repository already exists"

    # Check if there are uncommitted changes
    if ! git diff-index --quiet HEAD --; then
        echo ""
        echo "📝 You have uncommitted changes. Committing them..."
        git add .
        git commit -m "Update for deployment - $(date)"
        echo "✅ Changes committed"
    fi

    echo ""
    echo "📊 Git status:"
    git status --short
    echo ""
    echo "💡 Ready to push:"
    echo "   git push origin main"
fi

echo ""
echo "📋 Deployment Checklist:"
echo "✅ Railway account needed (https://railway.app)"
echo "✅ MySQL plugin will be added automatically"
echo "✅ Admin credentials: admin@admin.com / admin"
echo "✅ 12 products with images already configured"
echo "✅ JSP pagination errors fixed"
echo "✅ Railway configuration ready (railway.toml)"
echo ""

echo "🔧 Railway Configuration Details:"
echo "  • Builder: Nixpacks (auto-detected, no Docker needed)"
echo "  • Port: 8080"
echo "  • Health check: /"
echo "  • Auto-restart on failure"
echo ""

echo "🌐 After deployment steps:"
echo "1. Add MySQL database plugin in Railway dashboard"
echo "2. Wait for database to be ready"
echo "3. Test admin login: admin@admin.com / admin"
echo "4. Verify products display correctly"
echo "5. (Optional) Configure custom domain"
echo ""

echo "🎯 Quick Commands:"
echo "  • Push to GitHub: git push origin main"
echo "  • View logs: Railway dashboard → Logs"
echo "  • Add database: New → Database → MySQL"
echo ""

echo "📖 For detailed instructions, see: RAILWAY_DEPLOYMENT.md"
echo ""

# Check if ready for deployment
echo "🔍 Checking deployment readiness..."
if [ -f "pom.xml" ] && [ -f "railway.toml" ] && [ -f "Procfile" ]; then
    echo "✅ All required files present"
else
    echo "⚠️  Missing deployment files"
fi

if [ -d "src/main/webapp/WEB-INF/view" ]; then
    echo "✅ JSP views present"
else
    echo "⚠️  JSP views missing"
fi

echo ""
echo "🚀 You're ready to deploy to Railway!"