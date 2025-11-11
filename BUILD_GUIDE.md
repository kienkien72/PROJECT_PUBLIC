# 📚 Build & Run Guide - Laptop Shop E-commerce Platform

This guide provides step-by-step instructions to set up, build, and run the Laptop Shop e-commerce application.

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

### Required Software
- **Java Development Kit (JDK) 17** or later
  ```bash
  # Check Java version
  java -version
  # Should show: java version "17.x.x"
  ```

- **Apache Maven 3.9.5** or later (included via Maven wrapper)
  ```bash
  # Check Maven version (if using system Maven)
  mvn -version
  ```

- **MySQL Server 8.0** or later
  ```bash
  # Check MySQL version
  mysql --version
  ```

### Development Tools (Optional)
- IDE: IntelliJ IDEA, Spring Tool Suite, Eclipse, or VS Code
- Database GUI: MySQL Workbench, DBeaver, or phpMyAdmin
- Git for version control

---

## 🚀 Quick Start (5 Minutes)

If you have all prerequisites installed:

```bash
# 1. Clone the repository
git clone https://github.com/kienkien72/Spring-MVC.git
cd Spring-MVC/ProjectRestAPI

# 2. Create database
mysql -u root -p
CREATE DATABASE laptopshop;

# 3. Run the application
./mvnw spring-boot:run

# 4. Access the application
# Open browser: http://localhost:8080
```

---

## 📝 Detailed Setup Instructions

### Step 1: Database Setup

1. **Start MySQL Server**
   ```bash
   # On Ubuntu/Debian
   sudo systemctl start mysql

   # On macOS (with Homebrew)
   brew services start mysql

   # On Windows
   net start mysql
   ```

2. **Login to MySQL**
   ```bash
   mysql -u root -p
   # Enter password: 123456 (as configured in application.properties)
   ```

3. **Create Database**
   ```sql
   CREATE DATABASE laptopshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   SHOW DATABASES;
   EXIT;
   ```

4. **Verify Database Connection** (Optional)
   ```bash
   mysql -u root -p123456 -h localhost laptopshop
   ```

### Step 2: Project Configuration

1. **Verify Database Configuration**

   Check `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/laptopshop
   spring.datasource.username=root
   spring.datasource.password=123456
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```

2. **Environment Variables** (Optional)
   ```bash
   # If MySQL is on a different host
   export MYSQL_HOST=your-mysql-host

   # On Windows
   set MYSQL_HOST=your-mysql-host
   ```

### Step 3: Build and Run

#### Option 1: Using Maven Wrapper (Recommended)
```bash
# Navigate to project directory
cd ProjectRestAPI

# Clean and compile
./mvnw clean compile

# Run the application
./mvnw spring-boot:run

# For Windows
mvnw.cmd spring-boot:run
```

#### Option 2: Using System Maven
```bash
# If you have Maven installed globally
mvn clean spring-boot:run

# Or with more details
mvn clean compile spring-boot:run
```

#### Option 3: Running from JAR
```bash
# Package the application
./mvnw clean package

# Run the JAR file
java -jar target/laptopshop-0.0.1-SNAPSHOT.jar

# Run with specific profile
java -jar target/laptopshop-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Step 4: Verify Application

1. **Check Application Logs**
   Look for these messages:
   ```
   Started LaptopshopApplication in X.xxx seconds
   Tomcat started on port(s): 8080 (http)
   ```

2. **Access the Application**
   - **Home Page**: http://localhost:8080
   - **Login Page**: http://localhost:8080/login
   - **Admin Panel**: http://localhost:8080/admin (requires admin login)

3. **Test Database Connection**
   - The application should create tables automatically
   - Check MySQL: `USE laptopshop; SHOW TABLES;`

---

## 🔧 Development Workflow

### Running in Development Mode
```bash
# With Spring DevTools (auto-restart on changes)
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

### Building for Production
```bash
# Clean build without tests
./mvnw clean package -DskipTests

# Optimize for production
./mvnw clean package -Pprod -DskipTests
```

### Running Tests
```bash
# Run all tests
./mvnw test

# Run specific test
./mvnw test -Dtest=LaptopshopApplicationTests

# Run with coverage
./mvnw clean test jacoco:report
```

### Debug Mode
```bash
# Run with remote debugging enabled
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

---

## 🏗️ Project Structure Overview

```
ProjectRestAPI/
├── src/
│   ├── main/
│   │   ├── java/vn/ndkien/laptopshop/
│   │   │   ├── controller/        # Web controllers (admin/client)
│   │   │   ├── domain/           # JPA entities
│   │   │   ├── repository/       # Data access layer
│   │   │   ├── service/          # Business logic
│   │   │   └── config/           # Security and configuration
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       ├── WEB-INF/view/     # JSP files
│   │       └── resources/        # Static assets
│   └── test/                     # Test classes
├── target/                       # Build output
├── pom.xml                       # Maven configuration
└── README.md                     # Project documentation
```

---

## 🐛 Troubleshooting

### Common Issues and Solutions

#### 1. **Port 8080 Already in Use**
```bash
# Find process using port 8080
netstat -tulpn | grep :8080
# or on Windows
netstat -ano | findstr :8080

# Kill the process
kill -9 <PID>
# or on Windows
taskkill /PID <PID> /F

# Or run on different port
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

#### 2. **Database Connection Failed**
```bash
# Check MySQL service status
sudo systemctl status mysql

# Start MySQL service
sudo systemctl start mysql

# Check database exists
mysql -u root -p -e "SHOW DATABASES;"

# Reset MySQL password if needed
mysql -u root -p -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';"
```

#### 3. **Java Version Mismatch**
```bash
# Check current Java version
java -version

# Set JAVA_HOME (Linux/macOS)
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Set JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

#### 4. **Maven Build Failures**
```bash
# Clean Maven cache
./mvnw clean

# Force update dependencies
./mvnw clean compile -U

# Check Maven version
./mvnw -version
```

#### 5. **Permission Issues (Linux/macOS)**
```bash
# Make Maven wrapper executable
chmod +x mvnw

# Check file permissions
ls -la mvnw
```

#### 6. **Application Not Starting**
Check the logs for these common errors:
- **Table doesn't exist**: Database schema issue - let Hibernate create it
- **Bean creation error**: Missing or incorrect configuration
- **Out of memory**: Increase JVM heap size
  ```bash
  ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx2g"
  ```

### Getting Help

1. **Check Application Logs**
   ```bash
   # Run with verbose logging
   ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.org.springframework=DEBUG"
   ```

2. **Verify Configuration**
   ```bash
   # Check active profile
   curl http://localhost:8080/actuator/info
   ```

3. **Database Diagnostics**
   ```sql
   -- Check tables
   USE laptopshop;
   SHOW TABLES;

   -- Check users table
   DESCRIBE user;
   SELECT * FROM user;
   ```

---

## 🎯 Default Users and Access

After running the application for the first time:

### Creating Admin User
1. Register a new account at http://localhost:8080/register
2. Manually update role in database:
   ```sql
   USE laptopshop;
   UPDATE user SET role = 'ADMIN' WHERE email = 'your-email@example.com';
   ```

### Default Access
- **Home**: http://localhost:8080/
- **Login**: http://localhost:8080/login
- **Register**: http://localhost:8080/register
- **Products**: http://localhost:8080/products
- **Admin Dashboard**: http://localhost:8080/admin (requires admin role)

---

## 📊 Performance and Monitoring

### Monitoring Endpoints (if enabled)
```bash
# Health check
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info

# Metrics
curl http://localhost:8080/actuator/metrics
```

### JVM Monitoring
```bash
# Monitor with JVisualVM (included in JDK)
jvisualvm

# Or with JConsole
jconsole localhost:8080
```

---

## 🚀 Next Steps

Once the application is running:

1. **Explore Features**
   - Browse products as guest
   - Register and login
   - Add items to cart
   - Place orders

2. **Admin Features**
   - Login as admin
   - Manage products
   - View orders
   - Manage users

3. **Development**
   - Check out `CLAUDE.md` for development guidelines
   - Review the code structure
   - Start building new features

For questions or issues, refer to the project's README.md or check the application logs for detailed error messages.