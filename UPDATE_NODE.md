# 🔄 Update Node.js Guide

Complete guide to update Node.js to the latest version for the Laptop Shop project.

## 📋 Current Requirements

- **Minimum Node.js**: 18.x
- **Recommended**: 20.x LTS (Latest Long Term Support)
- **Latest**: 22.x (if you want cutting-edge features)

## 🔍 Check Current Node.js Version

```bash
node --version
# Output examples:
# v16.20.0 (needs update)
# v18.19.0 (minimum OK)
# v20.11.0 (recommended)
# v22.1.0 (latest)
```

## 🚀 Update Methods

### Method 1: Using nvm (Recommended - Best for Developers)

nvm (Node Version Manager) allows you to install and switch between multiple Node.js versions.

#### Install nvm (if not already installed)
```bash
# Download and install nvm
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash

# Or using wget
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash

# Reload your terminal
source ~/.bashrc
# OR restart your terminal

# Verify nvm installation
nvm --version
```

#### Update Node.js with nvm
```bash
# Install the latest LTS (recommended)
nvm install --lts

# Or install a specific version
nvm install 20.11.0

# Use the installed version
nvm use --lts

# Set as default
nvm alias default node

# Verify update
node --version
npm --version
```

#### List available versions
```bash
# List all LTS versions
nvm ls-remote --lts

# List all versions
nvm ls-remote

# List installed versions
nvm ls
```

### Method 2: Using n (Alternative Version Manager)

```bash
# Install n (if not installed)
npm install -g n

# Update to latest LTS
n lts

# Or update to latest version
n latest

# Or update to a specific version
n 20.11.0

# Verify
node --version
```

### Method 3: Official Installer (Simplest for Beginners)

1. Go to [nodejs.org](https://nodejs.org/)
2. Download the **LTS** version
3. Run the installer
4. It will automatically replace your old version

### Method 4: Package Managers

#### Ubuntu/Debian
```bash
# Remove old version
sudo apt remove nodejs npm

# Add NodeSource repository for latest version
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -

# Install Node.js
sudo apt-get install -y nodejs

# Verify
node --version
npm --version
```

#### macOS with Homebrew
```bash
# Update Homebrew first
brew update

# Upgrade Node.js
brew upgrade node

# If you don't have Node.js installed
brew install node

# Verify
node --version
npm --version
```

#### Windows with Chocolatey
```powershell
# Update Node.js
choco upgrade nodejs-lts

# Or install if not present
choco install nodejs-lts

# Verify
node --version
npm --version
```

#### Windows with Scoop
```powershell
# Update Node.js
scoop update nodejs-lts

# Or install
scoop install nodejs-lts

# Verify
node --version
npm --version
```

## 🛠️ After Updating Node.js

### 1. Update npm (comes with Node.js but good to check)
```bash
npm install -g npm@latest
```

### 2. Clean and reinstall project dependencies
```bash
# Go to your project directory
cd /path/to/Spring-MVC/ProjectRestAPI

# Clean existing dependencies
rm -rf node_modules
rm package-lock.json

# Reinstall dependencies
npm install
```

### 3. Update global packages
```bash
# Check outdated global packages
npm outdated -g

# Update global packages
npm update -g

# Common packages to update
npm update -g wrangler
npm update -g vite
npm update -g @vercel/cli
```

## 🔧 Troubleshooting

### Issue 1: Multiple Node.js versions
```bash
# Check all installed Node.js versions
which node
where node  # Windows

# Check npm location
which npm
where npm  # Windows

# If conflicts, use nvm to manage
nvm use --lts
```

### Issue 2: Permission errors (Linux/macOS)
```bash
# Fix npm permissions
sudo chown -R $(whoami) ~/.npm
sudo chown -R $(whoami) /usr/local/lib/node_modules

# Or use nvm (avoids permission issues)
```

### Issue 3: npm doesn't work after update
```bash
# Reinstall npm
npm install -g npm@latest

# Or clear npm cache
npm cache clean --force
```

### Issue 4: Projects won't build after update
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and package-lock.json
rm -rf node_modules package-lock.json

# Reinstall
npm install

# If still issues, update dependencies
npm update
```

## 📦 Update Project for New Node.js Version

### Update package.json engines
```json
{
  "engines": {
    "node": ">=18.0.0",
    "npm": ">=9.0.0"
  }
}
```

### Update .nvmrc file (create if not exists)
```bash
echo "20.11.0" > .nvmrc
```

### Update Dockerfile if using Docker
```dockerfile
FROM node:20.11-alpine
```

## ✅ Verify Everything Works

```bash
# Check versions
node --version  # Should be 20.x or higher
npm --version   # Should be 10.x or higher

# Test your project
cd cloudflare/worker
npm test  # If tests exist
npm run dev  # Should start without errors

# Test deployment
npm run deploy
```

## 🎯 Recommended Version for This Project

For the Laptop Shop e-commerce project, I recommend:

- **Node.js**: **20.11.0 LTS** (or latest 20.x)
- **npm**: **10.x** (comes with Node.js 20.x)

Why LTS?
- ✅ Stable and reliable
- ✅ Long-term support (until April 2026)
- ✅ Compatible with all Cloudflare Workers features
- ✅ Best performance for production

## 📚 Additional Tools

### Node.js Version Check Script
Create a script to check if Node.js version meets requirements:

```bash
# create check-node-version.sh
#!/bin/bash
REQUIRED_VERSION="20.0.0"
CURRENT_VERSION=$(node -v | sed 's/v//')

if [ "$(printf '%s\n' "$REQUIRED_VERSION" "$CURRENT_VERSION" | sort -V | head -n1)" = "$REQUIRED_VERSION" ]; then
    echo "✅ Node.js version $CURRENT_VERSION is OK"
else
    echo "❌ Node.js version $CURRENT_VERSION is too old. Please update to $REQUIRED_VERSION or higher"
fi
```

## 🔄 Automatic Updates

### Enable automatic Node.js updates (macOS)
```bash
# Using Homebrew
brew install node --cask  # Enables auto-update

# Or using Mac App Store
# Download Node.js from Mac App Store for auto-updates
```

### Windows Updates
- Download from Microsoft Store for automatic updates
- Or enable Chocolatey auto-update:
  ```powershell
  choco feature enable -n=autoUninstaller
  ```

## 📊 Node.js Release Schedule

| Version | Release Date | Maintenance Until | End of Life | Type |
|---------|--------------|-------------------|-------------|------|
| 18.x | Apr 2022 | Apr 2025 | Apr 2025 | Active LTS |
| 20.x | Apr 2023 | Apr 2026 | Apr 2026 | Active LTS |
| 22.x | Apr 2024 | Oct 2024 | Jun 2025 | Current |

---

**💡 Pro Tip**: Use nvm for development - it lets you switch between Node.js versions easily for different projects!