# Use Maven 3.9 image for building
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first for better Docker layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B || mvn dependency:resolve -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Use smaller runtime image
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the built JAR file
COPY --from=builder /app/target/laptopshop-*.jar app.jar

# Create non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
RUN chown -R spring:spring /app
USER spring

# Expose port
EXPOSE 8080

# Add JVM options for better memory management
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Start the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]