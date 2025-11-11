# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Java Spring Boot e-commerce platform** for a laptop shop with traditional MVC architecture using JSP views. The application follows standard Spring Boot patterns with clear separation between admin and client functionality.

**Tech Stack:**
- Java 17 + Spring Boot 3.2.2
- Spring Security with role-based access control
- Spring Data JPA with Hibernate
- MySQL database
- JSP + Bootstrap for frontend
- Maven build system

## Common Development Commands

### Running the Application
```bash
# Start the application (uses Maven wrapper)
./mvnw spring-boot:run

# The application runs on http://localhost:8080
```

### Build and Package
```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package as JAR
./mvnw clean package

# Run without tests
./mvnw clean package -DskipTests
```

### Database Setup
- MySQL database named `laptopshop` must exist
- Default connection: `localhost:3306`
- Username: `root`, Password: `123456`
- Configure via `MYSQL_HOST` environment variable if needed
- DDL auto: `update` (automatically updates schema)

## Architecture and Structure

### Package Structure
```
vn.ndkien.laptopshop/
├── config/          # Security and Web MVC configuration
├── controller/      # Split into admin/ and client/ packages
├── domain/          # JPA entities (User, Product, Order, Cart, etc.)
├── repository/      # Spring Data JPA repositories
└── service/         # Business logic layer
```

### Key Architectural Patterns

**Security Configuration:**
- Spring Security with method-level security (`@EnableMethodSecurity`)
- BCrypt password encoding
- Custom `UserDetailsService` implementation
- Role-based access control (ADMIN/USER roles)
- Session management with JDBC storage
- Custom authentication success handler for role-based redirection

**Controller Pattern:**
- Controllers are split by role: `admin/` and `client/`
- Admin controllers: Dashboard, Product, User, Order management
- Client controllers: Homepage, Product details, Cart functionality
- Uses Spring MVC with `@Controller` and JSP views

**Service Layer:**
- Service classes handle business logic
- Uses constructor injection
- Implements pagination with `Pageable`
- Uses Specification pattern for dynamic queries (see `ProductSpec`)

**Entity Model:**
- JPA entities with validation annotations
- Bidirectional relationships with proper cascade handling
- Composite keys in `OrderDetail` and `CartDetail`
- Custom validators for password strength and registration

### Database Schema
The application manages 7 main entities:
- **User**: Authentication and profile management
- **Product**: Catalog with filtering and search
- **Order/OrderDetail**: Order management system
- **Cart/CartDetail**: Shopping cart functionality
- **Role**: User authorization roles

### File Upload Configuration
- Max file size: 50MB
- Multipart request size: 50MB
- Upload service handles product images

### Session Management
- JDBC session storage
- Session timeout: 30 minutes
- Remember-me functionality available

## Development Guidelines

### Adding New Features
1. Follow the existing package structure and naming conventions
2. Create repositories extending `JpaRepository`
3. Implement service classes with `@Service` annotation
4. Create controllers with appropriate role-based access
5. Add corresponding JSP views in `/WEB-INF/view/admin/` or `/WEB-INF/view/client/`

### Security Considerations
- All admin endpoints require ADMIN role
- Use `@PreAuthorize("hasRole('ADMIN')")` for method-level security
- Public endpoints configured in `SecurityConfig.java`
- Form-based authentication with custom login/logout

### Testing
- Currently only basic Spring Boot test exists
- Add unit tests for service layer and integration tests for controllers
- Test database configuration should use H2 or test MySQL database

### Validation
- Use Jakarta Bean Validation annotations
- Custom validators for complex business rules
- Server-side validation in controllers using `@Valid`

### API Endpoints
The application uses traditional MVC pattern, not REST APIs. All endpoints return JSP views or redirect to other pages.

## Important Configuration Details

- **Server**: Runs on 0.0.0.0:8080 (accessible from all interfaces)
- **Database**: MySQL with connection pooling via HikariCP
- **View Resolution**: JSP files in `/WEB-INF/view/`
- **Static Resources**: Located in `/webapp/resources/`
- **Logging**: Spring Security debug logging enabled