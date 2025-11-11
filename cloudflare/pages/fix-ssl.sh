#!/bin/bash

echo "🔒 Fixing SSL/HTTPS Issues"
echo "========================="

# Clean and rebuild
echo "🧹 Cleaning and rebuilding..."
npm run clean
npm run build

# Add security headers
echo "📝 Adding security headers..."
cat > dist/_headers << 'EOF'
/*
  X-Frame-Options: DENY
  X-Content-Type-Options: nosniff
  Referrer-Policy: strict-origin-when-cross-origin
  Permissions-Policy: camera=(), microphone=(), geolocation=()
  Strict-Transport-Security: max-age=31536000; includeSubDomains; preload
  Access-Control-Allow-Origin: *
  Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
  Access-Control-Allow-Headers: Content-Type, Authorization
EOF

# Add redirects for SPA
echo "🔄 Adding redirects..."
cat > dist/_redirects << 'EOF'
/*    /index.html   200
/api/*  https://laptopshop-api.your-subdomain.workers.dev/api/:splat  302
EOF

# Ensure index.html exists
if [ ! -f "dist/index.html" ]; then
    echo "⚠️  Creating default index.html..."
    cat > dist/index.html << 'EOF'
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Laptop Shop</title>
    <meta http-equiv="refresh" content="0; URL=/">
</head>
<body>
    <h1>Loading Laptop Shop...</h1>
</body>
</html>
EOF
fi

# Deploy
echo "🚀 Deploying with SSL fix..."
npx wrangler pages deploy dist

echo ""
echo "✅ SSL fix applied!"
echo ""
echo "If SSL still shows errors:"
echo "1. Wait 5-10 minutes for certificate propagation"
echo "2. Clear browser cache (Ctrl+Shift+Del)"
echo "3. Try in incognito mode"
echo "4. Check Cloudflare SSL settings in dashboard"