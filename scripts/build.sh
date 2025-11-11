#!/bin/bash

echo "🏗️  Building Laptop Shop..."
echo "============================"

# Clean and compile
echo "🧹 Cleaning and compiling..."
./mvnw clean compile

# Package
echo "📦 Packaging..."
./mvnw package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📁 JAR file created: target/laptopshop-0.0.1-SNAPSHOT.jar"
    echo ""
    echo "🚀 To run the built application:"
    echo "   java -jar target/laptopshop-0.0.1-SNAPSHOT.jar"
else
    echo "❌ Build failed!"
    exit 1
fi