# 📦 Installation Requirements

## Must-Have Installations

### 1. Java 17+ ⚡
**Required**: The application runs on Java 17 or higher.

**Install Java 17:**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# macOS (using Homebrew)
brew install openjdk@17

# Windows
# Download from: https://adoptium.net/temurin/releases/?version=17
# Run installer and add to PATH

# Verify installation
java -version
# Should show: java version "17.x.x"
```

### 2. MySQL Server 🗄️
**Required**: Database for storing products, users, orders.

**Install MySQL:**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql

# macOS (using Homebrew)
brew install mysql
brew services start mysql

# Windows
# Download from: https://dev.mysql.com/downloads/installer/
# Run installer (choose "Developer Default")

# Set root password (important!)
sudo mysql
ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';
EXIT;
```

### 3. Git 📚
**Required**: To clone the repository.

**Install Git:**
```bash
# Ubuntu/Debian
sudo apt install git

# macOS (using Homebrew)
brew install git

# Windows
# Download from: https://git-scm.com/download/win
# Run installer with default settings

# Verify installation
git --version
```

## That's It! 🎉

You only need **3 things** to run this project:
1. ✅ Java 17+
2. ✅ MySQL Server
3. ✅ Git

No need to install Maven separately - it's included with the project!

---

## 🚀 Quick Setup (5 minutes)

```bash
# 1. Clone project
git clone https://github.com/kienkien72/Spring-MVC.git
cd Spring-MVC/ProjectRestAPI

# 2. Create database
mysql -u root -p123456 -e "CREATE DATABASE laptopshop;"

# 3. Run project
chmod +x scripts/*.sh
./scripts/dev.sh

# OR use the npm-style commands:
./mvnw spring-boot:run  # Start the server
```

Then open: http://localhost:8080

---

## 🔧 Optional (but recommended)

### For Development:
- **VS Code** or **IntelliJ IDEA** (code editor)
- **MySQL Workbench** (database GUI)
- **Postman** (for API testing)

### All of these are FREE and optional!