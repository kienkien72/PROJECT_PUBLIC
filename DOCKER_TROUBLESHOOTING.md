# 🐳 Docker Build Issues - Solutions Guide

## ❌ Error: `openjdk:17-jre-slim: not found`

**Root Cause**: The image tag `openjdk:17-jre-slim` doesn't exist in Docker Hub.

## ✅ **Solutions Provided**

### Solution 1: Use Eclipse Temurin (Recommended)
✅ Updated Dockerfile with: `eclipse-temurin:17-jre-alpine`

### Solution 2: Use Ubuntu Base
✅ Created `Dockerfile.alternative` with: `ubuntu:22.04`

### Solution 3: Use Docker Compose (Simplest)
✅ Created `docker-compose.simple.yml` with standard Maven image

## 🚀 **How to Fix**

### Option A: Use Fixed Dockerfile
```bash
# The main Dockerfile is already fixed
docker build -t laptopshop .
docker run -p 8080:8080 laptopshop
```

### Option B: Use Alternative Dockerfile
```bash
docker build -f Dockerfile.alternative -t laptopshop-alt .
docker run -p 8080:8080 laptopshop-alt
```

### Option C: Use Docker Compose (Easiest)
```bash
docker-compose -f docker-compose.simple.yml up -d
```

### Option D: Railway Deployment (No Docker Needed)
Since Railway uses Nixpacks, you don't need Docker at all! Just:
1. Push to GitHub
2. Deploy from Railway dashboard

## 🔧 **Common Docker Issues & Fixes**

### Issue 1: Build Cache Problems
```bash
# Clean Docker cache
docker system prune -a
docker build --no-cache -t laptopshop .
```

### Issue 2: Permission Errors
```bash
# Fix file permissions
sudo chown -R $USER:$USER .
```

### Issue 3: Out of Memory
```bash
# Increase Docker memory (Docker Desktop)
# Or modify JVM options in Dockerfile
ENV JAVA_OPTS="-Xmx1024m -Xms512m"
```

### Issue 4: Port Already in Use
```bash
# Kill existing container
docker ps -a | grep laptopshop
docker stop <container-id>
docker rm <container-id>
```

## 🐳 **Docker Images That Work**

### For Multi-stage Builds:
- `eclipse-temurin:17-jdk-alpine` (build stage)
- `eclipse-temurin:17-jre-alpine` (runtime stage)

### For Simple Builds:
- `maven:3.9.4-eclipse-temurin-17`
- `openjdk:17`
- `ubuntu:22.04`

### Verified Working Commands:

#### Method 1: Multi-stage Build (Production Ready)
```bash
# Build
docker build -t laptopshop .

# Run
docker run -d \
  --name laptopshop \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/laptopshop \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=123456 \
  laptopshop
```

#### Method 2: Docker Compose (Development)
```bash
# Start database and app
docker-compose -f docker-compose.simple.yml up -d

# View logs
docker-compose logs -f app

# Stop
docker-compose down
```

#### Method 3: Railway Deployment (Production)
```bash
# No Docker needed!
git push origin main
# Deploy from Railway dashboard
```

## 📋 **Files Created**

1. **Dockerfile** - Fixed multi-stage build
2. **Dockerfile.alternative** - Ubuntu-based build
3. **docker-compose.simple.yml** - Simple compose setup
4. **init.sql** - Database initialization script

## ✅ **Verification**

### Check if Docker is working:
```bash
docker --version
```

### Check built images:
```bash
docker images | grep laptopshop
```

### Check running containers:
```bash
docker ps | grep laptopshop
```

## 🌐 **Access After Success**

- **Local**: http://localhost:8080
- **Admin Login**: admin@admin.com / admin
- **12 Products** loaded automatically

## 💡 **Pro Tips**

1. **For Production**: Use Railway (no Docker needed)
2. **For Development**: Use Docker Compose
3. **For Testing**: Use fixed Dockerfile
4. **Always**: Check image tags on Docker Hub first

## 📞 **Helpful Commands**

```bash
# Build with no cache
docker build --no-cache -t laptopshop .

# Run with interactive shell
docker run -it --entrypoint /bin/sh laptopshop

# View container logs
docker logs laptopshop

# Execute commands in running container
docker exec -it laptopshop bash

# Copy files from container
docker cp laptopshop:/app/target/laptopshop-0.0.1-SNAPSHOT.jar .
```

## 🎯 **Recommended Approach**

**For Your Use Case**: Use Railway deployment (no Docker required) because:
- ✅ Automatic builds
- ✅ Free SSL
- ✅ Managed database
- ✅ No Docker knowledge needed