#!/bin/bash

echo "🚀 Starting Laptop Shop Development Server..."
echo "=============================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17+ first."
    echo "📖 See INSTALL.md for installation instructions."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17+ is required. Current version: $(java -version 2>&1 | head -n1)"
    exit 1
fi

# Check if MySQL is running
if ! mysqladmin ping -h"localhost" --silent 2>/dev/null; then
    echo "⚠️  MySQL is not running. Starting MySQL..."
    if command -v systemctl &> /dev/null; then
        sudo systemctl start mysql
    elif command -v brew &> /dev/null; then
        brew services start mysql
    else
        echo "❌ Please start MySQL manually."
        exit 1
    fi
fi

# Check if database exists
if ! mysql -u root -p123456 -e "USE laptopshop;" 2>/dev/null; then
    echo "📦 Creating database..."
    mysql -u root -p123456 -e "CREATE DATABASE IF NOT EXISTS laptopshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
fi

echo "✅ All checks passed!"
echo "🌐 Server will be available at: http://localhost:8080"
echo "🛑 Press Ctrl+C to stop the server"
echo ""

# Start the application
./mvnw spring-boot:run