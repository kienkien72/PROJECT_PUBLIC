# 🚀 Quick Start Guide - Laptop Shop

Start the e-commerce platform in **3 simple steps**!

## 📋 Prerequisites (Install once)

1. **Java 17+**
   - Ubuntu: `sudo apt install openjdk-17-jdk`
   - macOS: `brew install openjdk@17`
   - Windows: Download from [adoptium.net](https://adoptium.net/temurin/releases/?version=17)

2. **MySQL Server**
   - Ubuntu: `sudo apt install mysql-server`
   - macOS: `brew install mysql`
   - Windows: Download from [dev.mysql.com](https://dev.mysql.com/downloads/installer/)

3. **Git**
   - Ubuntu: `sudo apt install git`
   - macOS: `brew install git`
   - Windows: Download from [git-scm.com](https://git-scm.com/download/win)

## ⚡ Quick Start (5 minutes)

```bash
# 1. Clone the project
git clone https://github.com/kienkien72/Spring-MVC.git
cd Spring-MVC/ProjectRestAPI

# 2. One-time setup
./scripts/setup.sh

# 3. Start the server
./scripts/dev.sh
```

🎉 **Done!** Open http://localhost:8080 in your browser

## 🎮 Common Commands (like npm/yarn)

### Development
```bash
./scripts/dev.sh          # Start development server
./scripts/build.sh        # Build the project
./scripts/test.sh         # Run tests
./scripts/setup.sh        # Run setup again
```

### Maven Commands (alternative)
```bash
./mvnw spring-boot:run    # Start server
./mvnw clean package      # Build project
./mvnw test              # Run tests
./mvnw clean compile     # Compile only
```

### Production
```bash
# Build for production
./scripts/build.sh

# Run the built JAR
java -jar target/laptopshop-0.0.1-SNAPSHOT.jar
```

## 🏗️ Project Structure

```
ProjectRestAPI/
├── scripts/              # Shell scripts for easy commands
│   ├── dev.sh           # Start dev server
│   ├── build.sh         # Build project
│   ├── test.sh          # Run tests
│   └── setup.sh         # Initial setup
├── src/
│   ├── main/java/       # Java source code
│   └── webapp/          # JSP views and static files
├── pom.xml              # Maven configuration
└── scripts.json         # Available commands (like package.json)
```

## 🔑 Default Access

- **Home Page**: http://localhost:8080
- **Register**: http://localhost:8080/register
- **Login**: http://localhost:8080/login
- **Admin**: After registering, update user role to 'ADMIN' in database

## 🛠️ Troubleshooting

### Server won't start?
```bash
# Check if port 8080 is free
netstat -tulpn | grep :8080

# Kill process on port 8080
sudo kill -9 $(sudo lsof -t -i:8080)
```

### Database connection error?
```bash
# Start MySQL
sudo systemctl start mysql

# Create database manually
mysql -u root -p123456 -e "CREATE DATABASE laptopshop;"
```

### Permission denied?
```bash
# Make scripts executable
chmod +x scripts/*.sh
```

## 📚 More Documentation

- `INSTALL.md` - Detailed installation guide
- `BUILD_GUIDE.md` - Comprehensive build instructions
- `CLAUDE.md` - Development guidelines for contributors

---

**Need help?** Check the console output for detailed error messages or run `./scripts/setup.sh` again.