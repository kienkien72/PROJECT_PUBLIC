#!/bin/bash

echo "🐳 Docker Deployment Script"
echo "==========================="

# Configuration
IMAGE_NAME="laptopshop"
CONTAINER_NAME="laptopshop-app"
DB_CONTAINER="laptopshop-db"

# Build Docker image
echo "🏗️ Building Docker image..."
docker build -t $IMAGE_NAME .

# Stop existing containers
echo "🛑 Stopping existing containers..."
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

# Create network if not exists
docker network create laptopshop-network 2>/dev/null || true

# Start database if not running
if ! docker ps -q -f name=$DB_CONTAINER | read -r; then
    echo "🗄️ Starting MySQL database..."
    docker run -d \
        --name $DB_CONTAINER \
        --network laptopshop-network \
        -e MYSQL_ROOT_PASSWORD=laptopshop123 \
        -e MYSQL_DATABASE=laptopshop \
        -e MYSQL_USER=laptopshop \
        -e MYSQL_PASSWORD=laptopshop123 \
        -p 3306:3306 \
        mysql:8.0

    echo "⏳ Waiting for database to start..."
    sleep 30
fi

# Start application
echo "🚀 Starting application..."
docker run -d \
    --name $CONTAINER_NAME \
    --network laptopshop-network \
    -p 8080:8080 \
    -e SPRING_DATASOURCE_URL=jdbc:mysql://$DB_CONTAINER:3306/laptopshop \
    -e SPRING_DATASOURCE_USERNAME=laptopshop \
    -e SPRING_DATASOURCE_PASSWORD=laptopshop123 \
    -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
    $IMAGE_NAME

echo ""
echo "✅ Docker deployment complete!"
echo ""
echo "🌐 Application: http://localhost:8080"
echo "🗄️ Database: localhost:3306"
echo "👤 Database User: laptopshop"
echo "🔑 Database Password: laptopshop123"
echo ""
echo "📊 View logs:"
echo "  docker logs -f $CONTAINER_NAME"
echo ""
echo "🛑 Stop containers:"
echo "  docker stop $CONTAINER_NAME $DB_CONTAINER"
echo "  docker rm $CONTAINER_NAME $DB_CONTAINER"