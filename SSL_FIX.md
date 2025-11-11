# 🔒 Fix SSL/HTTPS Connection Error

Error: `This site can't provide a secure connection` - `ERR_SSL_VERSION_OR_CIPHER_MISMATCH`

## 🚨 Quick Fix

### Option 1: Access via HTTP (Temporary)
```
http://75d03452.laptopshop-pages.pages.dev
```

### Option 2: Force HTTPS in Cloudflare Dashboard
1. Go to [Cloudflare Dashboard](https://dash.cloudflare.com)
2. Select your domain
3. Go to **SSL/TLS** → **Overview**
4. Set SSL to **Full (Strict)**
5. Go to **SSL/TLS** → **Edge Certificates**
6. Enable **Always Use HTTPS**

### Option 3: Redeploy with Proper Configuration
```bash
cd cloudflare/pages
npm run clean
npm run build
npx wrangler pages deploy dist --project-name laptopshop
```

## 🔍 Root Causes

1. **New Deployment**: SSL certificate hasn't propagated yet (wait 5-10 minutes)
2. **Missing Custom Headers**: Need to set HSTS header
3. **Cloudflare Settings**: SSL mode might be set to Flexible
4. **Empty Site**: Pages needs at least an index.html file

## 🛠️ Permanent Solutions

### Solution 1: Add _headers File
Create `cloudflare/pages/dist/_headers`:
```
/*
  X-Frame-Options: DENY
  X-Content-Type-Options: nosniff
  Referrer-Policy: strict-origin-when-cross-origin
  Permissions-Policy: camera=(), microphone=(), geolocation=()
  Strict-Transport-Security: max-age=31536000; includeSubDomains; preload
```

### Solution 2: Add _redirects File
Create `cloudflare/pages/dist/_redirects`:
```
/*    /index.html   200
```

### Solution 3: Update wrangler.toml
Add to `cloudflare/pages/wrangler.toml`:
```toml
[compatibility_flags]
nodejs_compat = true

[[redirects]]
from = "/*"
to = "/index.html"
status = 200

[env.production]
compatibility_date = "2024-01-01"

[env.production.vars]
FORCE_HTTPS = "true"
```

## 📝 Complete Fix Script

Create `cloudflare/pages/fix-ssl.sh`:
```bash
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
```

## 🚀 Deployment Commands

### After Fix:
```bash
cd cloudflare/pages
chmod +x fix-ssl.sh
./fix-ssl.sh
```

### Manual Deployment:
```bash
# Build with headers
npm run build

# Create headers file
cat > dist/_headers << 'EOF'
/*
  X-Frame-Options: DENY
  X-Content-Type-Options: nosniff
  Strict-Transport-Security: max-age=31536000
EOF

# Deploy
npx wrangler pages deploy dist
```

## 🔍 Debugging Steps

1. **Wait for Propagation**: SSL certs take 5-10 minutes
2. **Clear Browser Cache**: Ctrl+Shift+Del
3. **Try Incognito Mode**: Bypasses cache
4. **Check Browser**: Try different browser
5. **Check Status**: [Cloudflare Status](https://www.cloudflarestatus.com/)

## 🌐 Alternative URLs

If the main URL doesn't work:
- **HTTP**: `http://75d03452.laptopshop-pages.pages.dev`
- **Direct**: `https://75d03452.laptopshop-pages.pages.dev`
- **Custom**: Set up custom domain

## ✅ Verification

After applying fixes:
1. Deploy with `./fix-ssl.sh`
2. Wait 5-10 minutes
3. Clear browser cache
4. Try HTTPS URL again

The site should load securely with HTTPS! 🔒