#!/bin/bash

echo "🔧 Setting up Laptop Shop Project..."
echo "===================================="

# Check if we're in the right directory
if [ ! -f "pom.xml" ]; then
    echo "❌ Please run this script from the project root directory (where pom.xml is located)"
    exit 1
fi

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed"
    echo "📖 Please install Java 17+: https://adoptium.net/temurin/releases/?version=17"
    exit 1
fi

echo "✅ Java found: $(java -version 2>&1 | head -n1)"

# Check Maven wrapper
if [ ! -f "mvnw" ]; then
    echo "❌ Maven wrapper not found"
    exit 1
fi

# Make scripts executable
echo "📝 Making scripts executable..."
chmod +x scripts/*.sh

# Check MySQL
if ! command -v mysql &> /dev/null; then
    echo "⚠️  MySQL is not installed"
    echo "📖 Please install MySQL: https://dev.mysql.com/downloads/mysql/"
    echo ""
    echo "After installation, run this script again to complete setup."
    exit 1
fi

echo "✅ MySQL found: $(mysql --version)"

# Create database if it doesn't exist
echo "📦 Creating database..."
mysql -u root -p123456 -e "CREATE DATABASE IF NOT EXISTS laptopshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Database 'laptopshop' is ready"
else
    echo "❌ Failed to create database. Please check MySQL connection."
    echo "   Make sure MySQL is running and root password is '123456'"
    echo ""
    echo "   To reset MySQL password:"
    echo "   sudo mysql"
    echo "   ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';"
    exit 1
fi

# Build project
echo ""
echo "🏗️  Building project..."
./mvnw clean compile -q

if [ $? -eq 0 ]; then
    echo ""
    echo "🎉 Setup complete!"
    echo ""
    echo "🚀 To start the development server:"
    echo "   ./scripts/dev.sh"
    echo ""
    echo "   Or use npm-style commands:"
    echo "   ./mvnw spring-boot:run"
    echo ""
    echo "🌐 Application will be available at: http://localhost:8080"
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi