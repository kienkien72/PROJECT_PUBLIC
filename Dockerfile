# Use Maven 3.9 image for building
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first for better Docker layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use smaller runtime image
FROM openjdk:17-jre-slim

# Install MySQL client for health checks
RUN apt-get update && apt-get install -y mysql-client && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the built JAR file
COPY --from=builder /app/target/laptopshop-*.jar app.jar

# Create non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
RUN chown -R spring:spring /app
USER spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD mysqladmin ping -h"$DATABASE_HOST" -u"$DATABASE_USERNAME" -p"$DATABASE_PASSWORD" --silent || exit 1

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]