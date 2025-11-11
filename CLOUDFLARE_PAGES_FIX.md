# 🔧 Cloudflare Pages Deployment Fix

Fixed the deployment error: "Must specify a directory of assets to deploy"

## 🚀 Quick Fix

### Option 1: Run the Fix Script (Easiest)
```bash
./scripts/fix-cloudflare-pages.sh
```

### Option 2: Manual Steps
```bash
cd cloudflare/pages
npm install
npm run build
npx wrangler pages deploy dist
```

## ✅ What Was Fixed

1. **Added `wrangler.toml` configuration** with `pages_build_output_dir = "dist"`
2. **Created `package.json`** with build scripts
3. **Created `dist/` directory** for build output
4. **Fixed deployment command** to specify the `dist` directory

## 📁 Project Structure After Fix

```
cloudflare/pages/
├── assets/
│   ├── css/
│   └── js/
├── dist/              ← Build output directory
│   ├── index.html
│   ├── assets/
│   └── functions/
├── package.json
├── wrangler.toml
└── index.html
```

## 🎯 Available Commands

Navigate to `cloudflare/pages` directory:

```bash
# Install dependencies
npm install

# Build static files
npm run build

# Serve locally for development
npm run dev

# Deploy to Cloudflare Pages
npm run deploy

# Deploy to production
npm run deploy:prod

# Preview locally before deploy
npm run preview

# Clean build directory
npm run clean
```

## 🌐 Deployment Commands

### From cloudflare/pages directory:
```bash
# Deploy to Cloudflare Pages
npx wrangler pages deploy dist

# Deploy with custom project name
npx wrangler pages deploy dist --project-name laptopshop

# Deploy to production environment
npx wrangler pages deploy dist --env production
```

### From project root:
```bash
# Use the complete deployment script
./scripts/deploy-cloudflare.sh

# Or use the fixed Pages deployment
cd cloudflare/pages && npm run deploy
```

## 🔍 Troubleshooting

### Error: "No such file or directory: dist"
```bash
# Solution: Build first
npm run build
# OR
mkdir -p dist && cp -r * dist/
```

### Error: "wrangler.toml not found"
```bash
# Solution: You're in the wrong directory
cd cloudflare/pages
pwd  # Should show .../cloudflare/pages
```

### Error: "npm command not found"
```bash
# Solution: Install Node.js
# See NODE_SETUP.md for instructions
```

## 📝 Complete Deployment Workflow

```bash
# 1. Deploy Worker (Backend API)
cd cloudflare/worker
npm run deploy

# 2. Update API URL in frontend
cd ../..
node scripts/update-api-url.js https://your-worker.workers.dev

# 3. Deploy Pages (Frontend)
cd cloudflare/pages
npm run deploy
```

## ✅ Verification

After deployment, check:
1. **Worker URL**: `https://your-worker.subdomain.workers.dev/api/products`
2. **Pages URL**: `https://laptopshop.pages.dev`
3. **API connectivity**: Check browser console for any errors

## 🎉 Success!

Your Cloudflare Pages should now deploy successfully! The error has been resolved by properly configuring the build output directory.