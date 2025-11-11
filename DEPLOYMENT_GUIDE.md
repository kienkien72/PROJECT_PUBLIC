# 🚀 Deployment Guide - Laptop Shop E-commerce Platform

## 📋 Overview
Your Spring Boot e-commerce application needs a server environment to run. Below are the best deployment options:

## 🏆 Recommended Deployment Options

### 1. Railway (Easiest for Spring Boot)
✅ **Fully supports Java/Spring Boot**
✅ **Free tier available**
✅ **Automatic deployments from GitHub**
✅ **Built-in database support**

### 2. Render
✅ **Supports Java applications**
✅ **Free tier for web services**
✅ **PostgreSQL database included**
✅ **GitHub integration**

### 3. Heroku
✅ **Excellent Spring Boot support**
✅ **Easy deployment**
✅ **Add-ons for databases**

## 🎯 Quick Deploy to Railway (Recommended)

### Prerequisites
- GitHub account
- Railway account (sign up at [railway.app](https://railway.app))

### Step 1: Prepare for Deployment

1. **Create a GitHub repository**
```bash
git init
git add .
git commit -m "Initial commit - Laptop Shop E-commerce Platform"
# Create repository on GitHub and push
```

2. **Check Railway configuration** (already exists in your project)
```bash
cat railway.toml
```

### Step 2: Deploy to Railway

1. Go to [railway.app](https://railway.app) and login
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Railway will automatically detect it's a Java Spring Boot app
5. Configure environment variables:

**Environment Variables:**
```
MYSQL_HOST=containers-us-west-XXX.railway.app
MYSQL_DATABASE=railway
MYSQL_USERNAME=root
MYSQL_PASSWORD=YOUR_RAILWAY_PASSWORD
PORT=8080
```

6. Click "Deploy"

### Step 3: Set up Database on Railway

1. In Railway dashboard, add a new MySQL database service
2. Once provisioned, get the connection details
3. Update your application.properties or set environment variables

## 🐳 Alternative: Docker Deployment

### Build Docker Image
```bash
# Build the image
docker build -t laptopshop .

# Run locally to test
docker run -p 8080:8080 -e MYSQL_HOST=your-db-host laptopshop
```

### Deploy to Cloud Run (Google Cloud)
```bash
# Configure gcloud CLI
gcloud auth login
gcloud config set project your-project-id

# Build and deploy
gcloud builds submit --tag gcr.io/your-project-id/laptopshop
gcloud run deploy --image gcr.io/your-project-id/laptopshop --platform managed
```

## 📱 Important Configuration Updates

### 1. Update application.properties for production
```properties
# Production database configuration
spring.datasource.url=jdbc:mysql://${MYSQL_HOST}/railway
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}

# Server configuration
server.address=0.0.0.0
server.port=${PORT:8080}

# JPA configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# File upload (if needed)
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### 2. Handle File Uploads
For production, consider using:
- AWS S3 for file storage
- Cloudinary for image hosting
- Railway's volume storage

## 🔧 Pre-Deployment Checklist

1. **Database**
   - [ ] MySQL database created
   - [ ] Environment variables set
   - [ ] Initial data seeded (admin user, products)

2. **Application**
   - [ ] Application builds without errors
   - [ ] All dependencies in pom.xml
   - [ ] Port configured (8080 or from env var)
   - [ ] Server address set to 0.0.0.0

3. **Security**
   - [ ] Password encryption configured
   - [ ] HTTPS enabled (platform handles this)
   - [ ] Environment variables for secrets

## 🌐 Cloudflare Setup (Optional - For Custom Domain)

Once deployed on Railway/Render:

1. **Go to Cloudflare Dashboard**
2. **Add your domain**
3. **Point DNS to Railway/Render**
   - Type: CNAME
   - Name: @ (or www)
   - Target: your-app.railway.app
4. **Enable SSL/TLS** (Full mode)

## 📊 Monitoring

### Railway
- Built-in logs in dashboard
- Metrics and monitoring
- Error tracking

### Add-ons to Consider
- Sentry for error tracking
- LogRocket for user session recording
- Google Analytics for traffic

## 💰 Cost Breakdown

### Railway (Free Tier)
- $0/month for hobby plan
- 500 hours/month execution time
- 100GB data transfer
- Database included

### Render (Free Tier)
- $0/month for web service
- 750 hours/month
- PostgreSQL database free tier

## 🚨 Common Issues & Solutions

### 1. "Port already in use"
- Use environment variable PORT
- Railway automatically assigns ports

### 2. "Database connection failed"
- Check environment variables
- Ensure MySQL is running
- Verify host and credentials

### 3. "Out of memory"
- Add memory in Railway settings
- Optimize JVM settings: `-Xmx512m`

### 4. "File uploads not working"
- Use cloud storage solution
- Configure temporary directory

## 🎉 Next Steps After Deployment

1. Test all functionality
2. Set up custom domain
3. Configure analytics
4. Set up backups
5. Monitor performance
6. Scale as needed

## 📞 Support

- Railway: [docs.railway.app](https://docs.railway.app)
- Render: [render.com/docs](https://render.com/docs)
- Spring Boot: [spring.io/guides](https://spring.io/guides)