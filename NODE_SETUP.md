# 📦 Node.js Setup Guide - Laptop Shop

Complete Node.js setup instructions for the Laptop Shop e-commerce platform.

## 🎯 What You Need

### Node.js Version Requirements
- **Node.js**: 18.x or higher (recommended: 20.x LTS)
- **npm**: 9.x or higher (comes with Node.js)

## 🚀 Quick Install Node.js

### Option 1: Official Installer (Recommended)
1. Go to [nodejs.org](https://nodejs.org/)
2. Download the **LTS** version (Long Term Support)
3. Run the installer
4. Follow the installation wizard

### Option 2: Using Version Manager (nvm) - Developers Choice
```bash
# Install nvm (Node Version Manager)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash

# Reload your terminal
source ~/.bashrc

# Install Node.js LTS
nvm install --lts

# Set as default
nvm use --lts
nvm alias default node

# Verify installation
node --version
npm --version
```

### Option 3: Package Managers

#### Ubuntu/Debian
```bash
# Using NodeSource repository
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt-get install -y nodejs

# Verify
node --version
npm --version
```

#### macOS
```bash
# Using Homebrew
brew install node

# Verify
node --version
npm --version
```

#### Windows
```powershell
# Using Chocolatey
choco install nodejs-lts

# Or using Scoop
scoop install nodejs-lts

# Verify
node --version
npm --version
```

## ✅ Verify Installation

```bash
# Check Node.js version (should be 18+)
node --version
# Output: v20.x.x

# Check npm version
npm --version
# Output: 9.x.x

# Check npx (comes with npm)
npx --version
```

## 🔧 Project Setup with Node.js

### For Cloudflare Workers Deployment

```bash
# 1. Navigate to the worker directory
cd cloudflare/worker

# 2. Install dependencies
npm install

# 3. Install Wrangler CLI globally
npm install -g wrangler

# 4. Login to Cloudflare
wrangler login

# 5. Deploy
npm run deploy
```

### For Local Development

```bash
# 1. Install dependencies
npm install

# 2. Run development server
npm run dev

# 3. Or use Wrangler directly
wrangler dev
```

## 📚 Common Node.js Commands

### npm Commands
```bash
# Install all dependencies from package.json
npm install

# Install a specific package
npm install package-name

# Install globally
npm install -g package-name

# Install and save to package.json
npm install package-name --save

# Update packages
npm update

# Run scripts from package.json
npm run <script-name>

# List installed packages
npm list

# Remove a package
npm uninstall package-name
```

### npx Commands
```bash
# Run a package without installing
npx package-name

# Create a new project
npx create-react-app my-app
npx create-next-app my-app

# Run Wrangler without global install
npx wrangler@latest deploy
```

## 🛠️ Project-Specific Setup

### 1. Cloudflare Worker Setup
```bash
cd cloudflare/worker

# Install dependencies
npm install

# Available scripts:
npm run dev      # Start development server
npm run deploy   # Deploy to Cloudflare
npm run tail     # View logs
npm run db:init  # Initialize database
```

### 2. Frontend Build Tools
```bash
# If using build tools like Vite or Webpack
npm install -g vite
npm install -g webpack-cli

# Build for production
npm run build

# Start development
npm run dev
```

## 🔍 Troubleshooting

### Issue 1: "node: command not found"
```bash
# Solution: Add Node.js to PATH
# On Linux/macOS
echo 'export PATH="/usr/local/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

# On Windows
# Reinstall Node.js with "Add to PATH" option checked
```

### Issue 2: Permission Denied (Linux/macOS)
```bash
# Solution: Use nvm instead of system package manager
# Or fix npm permissions
mkdir ~/.npm-global
npm config set prefix '~/.npm-global'
echo 'export PATH=~/.npm-global/bin:$PATH' >> ~/.bashrc
source ~/.bashrc
```

### Issue 3: npm is slow
```bash
# Solution: Use faster registry
npm config set registry https://registry.npmmirror.com

# Or use yarn
npm install -g yarn
yarn install
```

### Issue 4: "Cannot find module" errors
```bash
# Solution: Clean install
rm -rf node_modules package-lock.json
npm install

# Clear npm cache
npm cache clean --force
```

### Issue 5: Wrangler not found
```bash
# Solution: Install Wrangler
npm install -g wrangler

# Or use npx
npx wrangler@latest --version
```

## 📦 Package.json Scripts

Your project includes these useful scripts:

```json
{
  "scripts": {
    "dev": "wrangler dev",
    "deploy": "wrangler deploy",
    "tail": "wrangler tail",
    "db:init": "wrangler d1 execute laptopshop-db --file=../database/schema.sql",
    "db:query": "wrangler d1 execute laptopshop-db --command",
    "db:migrate": "wrangler d1 migrations apply laptopshop-db --remote"
  }
}
```

## 🚀 Quick Start Guide

```bash
# 1. Install Node.js (if not installed)
# Visit nodejs.org and download LTS

# 2. Verify installation
node --version
npm --version

# 3. Clone the project
git clone https://github.com/kienkien72/Spring-MVC.git
cd Spring-MVC/ProjectRestAPI

# 4. Deploy to Cloudflare
./scripts/cloudflare-deploy.sh

# 5. Your app is live! 🎉
```

## 🎯 Next Steps

1. **Update API URL** in `cloudflare/pages/assets/js/api.js`
2. **Deploy Worker** to get your API endpoint
3. **Deploy Frontend** to Cloudflare Pages
4. **Configure Custom Domain** (optional)

## 📚 Additional Resources

- [Node.js Official Docs](https://nodejs.org/docs/)
- [npm Documentation](https://docs.npmjs.com/)
- [Wrangler CLI Guide](https://developers.cloudflare.com/workers/wrangler/)
- [Cloudflare Workers Docs](https://developers.cloudflare.com/workers/)

---

**💡 Tip**: Always use the LTS (Long Term Support) version of Node.js for production projects. It's more stable and supported longer.