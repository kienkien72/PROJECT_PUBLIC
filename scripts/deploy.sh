#!/bin/bash

echo "🚀 Deploy Laptop Shop to Cloud"
echo "================================"
echo ""
echo "Select deployment platform:"
echo "1) Railway (Recommended - Easiest)"
echo "2) Render (Docker + PostgreSQL)"
echo "3) Local Docker (for testing)"
echo "4) Build for manual deployment"
echo ""
read -p "Enter your choice (1-4): " choice

case $choice in
    1)
        echo "📦 Deploying to Railway..."
        echo ""

        # Check if Railway CLI is installed
        if ! command -v railway &> /dev/null; then
            echo "Installing Railway CLI..."
            npm install -g @railway/cli
        fi

        # Login to Railway
        echo "Please login to Railway:"
        railway login

        # Initialize project
        echo "Initializing Railway project..."
        railway init

        # Add MySQL database
        echo "Adding MySQL database..."
        railway add mysql

        # Deploy
        echo "Deploying to Railway..."
        railway up

        echo ""
        echo "✅ Deployment complete!"
        echo "🌐 Your app is now live!"
        railway open
        ;;

    2)
        echo "📦 Deploying to Render..."
        echo ""
        echo "1. Go to https://render.com"
        echo "2. Connect your GitHub repository"
        echo "3. Use the render.yaml configuration"
        echo "4. Set environment variables as needed"
        echo ""
        echo "📄 render.yaml has been configured for you!"
        echo "✅ Ready for Render deployment!"
        ;;

    3)
        echo "📦 Running with Docker..."
        echo ""

        # Check if Docker is installed
        if ! command -v docker &> /dev/null; then
            echo "❌ Docker is not installed. Please install Docker first."
            exit 1
        fi

        # Check if docker-compose is installed
        if ! command -v docker-compose &> /dev/null; then
            echo "❌ Docker Compose is not installed. Please install Docker Compose first."
            exit 1
        fi

        # Build and run
        echo "Building Docker image..."
        docker-compose build

        echo "Starting services..."
        docker-compose up -d

        echo ""
        echo "✅ Docker containers started!"
        echo "🌐 Application: http://localhost:8080"
        echo "📊 Database: localhost:3306"
        echo ""
        echo "To view logs: docker-compose logs -f"
        echo "To stop: docker-compose down"
        ;;

    4)
        echo "📦 Building for manual deployment..."
        echo ""

        # Build the JAR
        echo "Building JAR file..."
        ./mvnw clean package -DskipTests

        echo ""
        echo "✅ Build complete!"
        echo "📁 JAR file: target/laptopshop-0.0.1-SNAPSHOT.jar"
        echo ""
        echo "To run:"
        echo "java -jar target/laptopshop-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod"
        echo ""
        echo "Don't forget to set environment variables:"
        echo "export DATABASE_URL=jdbc:mysql://your-host:3306/your-db"
        echo "export DATABASE_USERNAME=your-username"
        echo "export DATABASE_PASSWORD=your-password"
        ;;

    *)
        echo "❌ Invalid choice. Please select 1-4."
        exit 1
        ;;
esac