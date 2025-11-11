#!/usr/bin/env node

/**
 * Script to update API_BASE_URL in frontend after deployment
 * Usage: node scripts/update-api-url.js <your-worker-url>
 */

const fs = require('fs');
const path = require('path');

// Get the worker URL from command line argument
const workerUrl = process.argv[2];

if (!workerUrl) {
    console.log('❌ Please provide your Worker URL');
    console.log('Usage: node scripts/update-api-url.js https://your-worker.your-subdomain.workers.dev');
    process.exit(1);
}

// Path to the API file
const apiFilePath = path.join(__dirname, '../cloudflare/pages/assets/js/api.js');

// Read the file
let content = fs.readFileSync(apiFilePath, 'utf8');

// Update the API_BASE_URL
const oldUrlPattern = /const API_BASE_URL = ["'][^"']+["'];/;
const newLine = `const API_BASE_URL = "${workerUrl}";`;

if (content.match(oldUrlPattern)) {
    content = content.replace(oldUrlPattern, newLine);

    // Write back to file
    fs.writeFileSync(apiFilePath, content);

    console.log('✅ API_BASE_URL updated successfully!');
    console.log(`📍 New URL: ${workerUrl}`);
    console.log('');
    console.log('📦 Now deploy your frontend:');
    console.log('   cd cloudflare/pages');
    console.log('   npx wrangler pages deploy');
} else {
    console.log('❌ Could not find API_BASE_URL in the file');
    console.log('Please check the file: cloudflare/pages/assets/js/api.js');
}