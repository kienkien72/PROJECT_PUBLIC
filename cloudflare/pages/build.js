#!/usr/bin/env node

/**
 * Build script for Cloudflare Pages
 * Copies files to dist directory without infinite loop
 */

const fs = require('fs');
const path = require('path');

const sourceDir = __dirname;
const distDir = path.join(sourceDir, 'dist');

// Create dist directory
if (!fs.existsSync(distDir)) {
    fs.mkdirSync(distDir, { recursive: true });
}

// Files and directories to copy
const itemsToCopy = [
    'index.html',
    'assets',
    '_headers',
    '_redirects'
];

// Copy each item
itemsToCopy.forEach(item => {
    const sourcePath = path.join(sourceDir, item);
    const destPath = path.join(distDir, item);

    if (fs.existsSync(sourcePath)) {
        console.log(`Copying ${item}...`);

        if (fs.statSync(sourcePath).isDirectory()) {
            // Copy directory recursively
            copyDir(sourcePath, destPath);
        } else {
            // Copy file
            fs.copyFileSync(sourcePath, destPath);
        }

        console.log(`✅ Copied ${item}`);
    } else {
        console.log(`⚠️  ${item} not found, skipping`);
    }
});

// Function to copy directory recursively
function copyDir(src, dest) {
    if (!fs.existsSync(dest)) {
        fs.mkdirSync(dest, { recursive: true });
    }

    const items = fs.readdirSync(src);

    items.forEach(item => {
        const srcPath = path.join(src, item);
        const destPath = path.join(dest, item);

        if (fs.statSync(srcPath).isDirectory()) {
            copyDir(srcPath, destPath);
        } else {
            fs.copyFileSync(srcPath, destPath);
        }
    });
}

console.log('✅ Build complete!');