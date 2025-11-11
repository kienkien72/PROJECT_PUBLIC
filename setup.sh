#!/bin/bash

echo "=== Setting up Java Spring Boot E-commerce Project ==="
echo

# Update package list
echo "1. Updating package list..."
sudo apt update

# Install Java 17
echo "2. Installing Java 17..."
sudo apt install openjdk-17-jdk -y

# Install Maven
echo "3. Installing Maven..."
sudo apt install maven -y

# Install MySQL Server
echo "4. Installing MySQL Server..."
sudo apt install mysql-server -y

# Start and enable MySQL
echo "5. Starting MySQL service..."
sudo systemctl start mysql
sudo systemctl enable mysql

# Configure MySQL with required password
echo "6. Configuring MySQL..."
echo "Setting MySQL root password to '123456' (as required by application)..."
sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';"

# Create database
echo "7. Creating laptopshop database..."
sudo mysql -u root -p123456 -e "CREATE DATABASE IF NOT EXISTS laptopshop;"

# Verify installations
echo
echo "=== Verification ==="
echo "Java version:"
java -version
echo
echo "Maven version:"
mvn -version
echo
echo "MySQL version (if accessible):"
mysql -u root -p123456 --version 2>/dev/null || echo "MySQL installed but version check requires authentication"
echo

# Grant privileges for remote connections (optional, if needed)
echo "8. Configuring MySQL for connections..."
sudo mysql -u root -p123456 -e "CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY '123456';"
sudo mysql -u root -p123456 -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;"
sudo mysql -u root -p123456 -e "FLUSH PRIVILEGES;"

echo
echo "=== Setup Complete! ==="
echo "You can now run the project with: ./mvnw spring-boot:run"
echo "The application will be available at: http://localhost:8080"