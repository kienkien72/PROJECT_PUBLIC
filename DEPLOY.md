# 🚀 Free Deployment Guide - Laptop Shop E-commerce

Deploy your Spring Boot e-commerce platform for FREE using these cloud providers.

## 🏆 Recommended Free Platforms

### 1. **Railway** ⭐ (Easiest)
- **Free Tier**: $5/month credit
- **Features**: Automatic deploys, MySQL database, custom domain
- **Link**: [railway.app](https://railway.app)

### 2. **Render**
- **Free Tier**: 750 hours/month
- **Features**: Free PostgreSQL, automatic SSL
- **Link**: [render.com](https://render.com)

### 3. **Vercel** (Static Frontend only)
- **Free Tier**: Unlimited
- **Best for**: JSP static hosting + external database

### 4. **Heroku** (Limited free)
- **Free Tier**: No longer available (paid only)

### 5. **Fly.io**
- **Free Tier**: 160 shared CPU-hours/month
- **Link**: [fly.io](https://fly.io)

### 6. **Google Cloud Run**
- **Free Tier**: 2 million requests/month
- **Link**: [cloud.google.com/run](https://cloud.google.com/run)

---

## 🎯 Option 1: Railway (Recommended - Easiest)

### Step 1: Prepare for Deployment
```bash
# Add Railway configuration
echo "railway = 'vn.ndkien.laptopshop.LaptopshopApplication'" > railway.toml
echo '{"build": {"builder": "NIXPACKS"}}' > .railway/config.json
```

### Step 2: Deploy
```bash
# Install Railway CLI
npm install -g @railway/cli

# Login to Railway
railway login

# Initialize project
railway init

# Add MySQL database
railway add mysql

# Deploy
railway up

# Open your app
railway open
```

### Environment Variables (Set in Railway dashboard):
```
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/railway
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

---

## 🎯 Option 2: Render (Second Choice)

### Step 1: Create Dockerfile
Already created in your project!

### Step 2: Deploy to Render
1. Go to [render.com](https://render.com)
2. Sign up with GitHub
3. Click "New +"
4. Select "Web Service"
5. Connect your GitHub repository
6. Use these settings:
   - **Environment**: Docker
   - **Instance Type**: Free
   - **Plan**: Free ($0/month)
7. Add PostgreSQL database:
   - Click "New +"
   - Select "PostgreSQL"
   - Free tier

### Environment Variables for Render:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/your_db
SPRING_DATASOURCE_USERNAME=your_user
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
```

---

## 🎯 Option 3: Docker + Free VPS (DigitalOcean/Oracle)

### Step 1: Get Free VPS
- **Oracle Cloud**: Always Free tier (2 AMD CPUs, 1GB RAM)
- **DigitalOcean**: Free $200 credit for 60 days
- **AWS**: Free tier (t2.micro for 12 months)

### Step 2: Deploy Docker
```bash
# Connect to your VPS
ssh root@your-server-ip

# Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Clone your project
git clone https://github.com/kienkien72/Spring-MVC.git
cd Spring-MVC/ProjectRestAPI

# Deploy with Docker Compose
docker-compose up -d

# Check logs
docker-compose logs -f
```

---

## 🎯 Option 4: Static Hosting (Vercel + PlanetScale)

### Frontend on Vercel
1. Extract JSP to static HTML (manual conversion)
2. Deploy to Vercel:
   ```bash
   npm i -g vercel
   vercel --prod
   ```

### Backend on PlanetScale
1. Sign up at [planetscale.com](https://planetscale.com)
2. Create free database
3. Update application.properties with PlanetScale URL

---

## 🎯 Option 5: Google Cloud Run + Cloud SQL

### Step 1: Build and Push to Google Container Registry
```bash
# Enable required APIs
gcloud services enable run.googleapis.com cloudbuild.googleapis.com

# Build image
gcloud builds submit --tag gcr.io/PROJECT-ID/laptopshop

# Deploy to Cloud Run
gcloud run deploy laptopshop \
  --image gcr.io/PROJECT-ID/laptopshop \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated
```

### Step 2: Set up Cloud SQL
```bash
# Create Cloud SQL instance
gcloud sql instances create laptopshop-db \
  --database-version=MYSQL_8_0 \
  --tier=db-f1-micro \
  --region=us-central1
```

---

## 📦 Pre-Deployment Checklist

### 1. Update Production Config
Create `application-prod.properties`:
```properties
# Production settings
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Security
server.address=0.0.0.0
server.port=${PORT:8080}

# Logging
logging.level.org.springframework.security=WARN
logging.level.org.springframework.web=WARN
```

### 2. Add Health Check Endpoint
Already configured in Dockerfile!

### 3. Configure Environment Variables
Required for all deployments:
- `DATABASE_URL` or `SPRING_DATASOURCE_URL`
- `DATABASE_USERNAME` or `SPRING_DATASOURCE_USERNAME`
- `DATABASE_PASSWORD` or `SPRING_DATASOURCE_PASSWORD`

### 4. Update File Upload Path
For production:
```properties
spring.servlet.multipart.location=/var/uploads
```

---

## 🔧 Common Deployment Issues

### Issue 1: Database Connection
```bash
# Test database connection
mysql -h YOUR_HOST -u YOUR_USER -p YOUR_DB
```

### Issue 2: Port Already in Use
```bash
# Kill process on port 8080
sudo fuser -k 8080/tcp
```

### Issue 3: Memory Issues
Add JVM options:
```bash
java -Xmx512m -jar app.jar
```

### Issue 4: File Upload Errors
Ensure upload directory exists:
```bash
mkdir -p /var/uploads
chmod 777 /var/uploads
```

---

## 📊 Comparison of Free Platforms

| Platform | Free Tier | Database | SSL | Custom Domain | Easy Setup |
|----------|-----------|----------|-----|---------------|------------|
| Railway | $5/month | MySQL ✅ | ✅ | ✅ | ⭐⭐⭐⭐⭐ |
| Render | 750h/mo | PostgreSQL ✅ | ✅ | ✅ | ⭐⭐⭐⭐ |
| Fly.io | 160h/mo | ❌ (separate) | ✅ | ✅ | ⭐⭐⭐ |
| Cloud Run | 2M req/mo | ❌ (separate) | ✅ | ✅ | ⭐⭐⭐ |
| Vercel | Unlimited | ❌ (separate) | ✅ | ✅ | ⭐⭐ |

---

## 🎉 Quick Deploy (Railway)

```bash
# 1. Install Railway CLI
npm install -g @railway/cli

# 2. Login and deploy
railway login
railway init
railway add mysql
railway up

# 3. Your app is live! 🚀
```

---

## 📝 Post-Deployment Tasks

1. **Update URLs in application**
2. **Set up monitoring**
3. **Configure backups**
4. **Add analytics**
5. **Test all features**

## 🆘 Getting Help

- Platform documentation
- Community forums
- Stack Overflow
- GitHub issues

---

**⭐ Tip**: Start with Railway for the easiest deployment. If you need more control, use Docker with a free VPS.