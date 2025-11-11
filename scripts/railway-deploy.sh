#!/bin/bash

echo "🚀 Quick Deploy to Railway"
echo "=========================="

# Check if Railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "📦 Installing Railway CLI..."
    curl -fsSL https://railway.app/install.sh | sh
    export PATH="$HOME/.local/bin:$PATH"
fi

# Login to Railway
echo "🔐 Please login to Railway (browser will open):"
railway login

# Initialize or link project
if [ ! -f ".railway/project.json" ]; then
    echo "📝 Creating new Railway project..."
    railway init
else
    echo "✅ Railway project already configured"
fi

# Add MySQL database if not exists
if ! railway status mysql &> /dev/null; then
    echo "🗄️ Adding MySQL database..."
    railway add mysql
    echo "⏳ Waiting for database to be ready..."
    sleep 30
fi

# Deploy application
echo "🚀 Deploying application..."
railway up

# Get deployment URL
echo ""
echo "✅ Deployment in progress..."
echo "📊 Check deployment status:"
railway status

echo ""
echo "🌐 Your application will be available at:"
railway domain

echo ""
echo "⏳ Deployment may take a few minutes to complete..."
echo "💡 Check logs with: railway logs"
echo "🔍 Open your app with: railway open"