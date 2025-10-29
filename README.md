# 🚀 CRM Backend

A modern Spring Boot-based CRM (Customer Relationship Management) backend with MongoDB Atlas and Google OAuth 2.0 authentication.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-green)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue)

## ✨ Features

- 🔐 **Google OAuth 2.0 Authentication** - Secure user authentication
- � **Customer Management** - Complete CRUD operations for customers
- 📧 **Campaign Management** - Create and manage marketing campaigns
- 🎯 **Audience Targeting** - Filter customers based on spending, visits, and activity
- 🛍️ **Order Tracking** - Track customer orders and purchase history
- 💬 **Communication Logs** - Record all customer communications
- 🔔 **Delivery Event System** - Real-time message delivery tracking (Pub/Sub)
- 🌐 **CORS Configured** - Ready for frontend integration

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Programming Language |
| Spring Boot | 3.5.0 | Application Framework |
| MongoDB Atlas | Cloud | Database (NoSQL) |
| Spring Security | 6.x | Authentication & Authorization |
| OAuth 2.0 | - | Google Authentication |
| Maven | 3.9+ | Build Tool & Dependency Management |

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

1. **Java 21** ☕
   - Download: [Eclipse Temurin](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
   - Verify installation: `java -version`

2. **Maven** (comes with the project via Maven Wrapper)
   - No separate installation needed
   - Verify: `.\mvnw.cmd --version` (Windows) or `./mvnw --version` (Mac/Linux)

3. **MongoDB Atlas Account** (Already configured) 🍃
   - Free tier available at [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
   - Connection string is pre-configured

4. **Google OAuth Credentials** 🔑
   - Get from [Google Cloud Console](https://console.cloud.google.com/)
   - Already set up in this project

## 🚀 Quick Start Guide

### Option 1: Using PowerShell Script (Recommended for Windows)

```powershell
# 1. Navigate to project directory
cd path\to\CRM_BACKEND-main

# 2. Run the application
.\run-local.ps1
```

The script will:
- ✅ Check for environment variables
- ✅ Start the Spring Boot application
- ✅ Connect to MongoDB Atlas
- ✅ Enable Google OAuth login

### Option 2: Using Environment Variables + Maven

```powershell
# 1. Navigate to project directory
cd path\to\CRM_BACKEND-main

# 2. Set environment variables
$env:MONGODB_URI = "your-mongodb-connection-string"
$env:GOOGLE_CLIENT_ID = "your-google-client-id"
$env:GOOGLE_CLIENT_SECRET = "your-google-client-secret"

# 3. Run with Maven Wrapper
.\mvnw.cmd spring-boot:run
```

### Option 3: Using .env.ps1 File (Best for Local Development)

```powershell
# 1. Create .env.ps1 file in project root
# Add the following content:

$env:MONGODB_URI = "mongodb+srv://username:password@cluster.mongodb.net/?appName=CRM"
$env:GOOGLE_CLIENT_ID = "your-google-client-id.apps.googleusercontent.com"
$env:GOOGLE_CLIENT_SECRET = "your-google-client-secret"

# 2. Load environment variables
. .\.env.ps1

# 3. Run the application
.\mvnw.cmd spring-boot:run
```

### Option 4: Run with Specific Profile

```powershell
# Run with local profile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local

# Run with test profile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=test
```

## 🌐 Access Points

Once the application is running:

- **API Base URL:** `http://localhost:8080`
- **Health Check:** `http://localhost:8080/actuator/health`
- **Google OAuth Login:** `http://localhost:8080/oauth2/authorization/google`
- **API Endpoints:** See [API Documentation](#-api-endpoints) below

## 📡 API Endpoints

### 🔐 Authentication
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/oauth2/authorization/google` | Initiate Google OAuth login | No |
| GET | `/login` | Login page redirect | No |
| GET | `/logout` | Logout user | Yes |
| GET | `/oauth/success` | OAuth success callback | No |
| GET | `/api/user` | Get current user info | Yes |

### 👥 Customer Management
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/customers` | List all customers | Yes |
| POST | `/api/customers` | Create new customer | Yes |
| PUT | `/api/customers/{id}` | Update customer | Yes |
| DELETE | `/api/customers/{id}` | Delete customer | Yes |
| POST | `/api/customers/filter` | Filter customers by criteria | Yes |

### 📧 Campaign Management
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/campaigns` | List all campaigns | Yes |
| POST | `/api/campaigns` | Create new campaign | Yes |
| GET | `/api/campaigns/{id}` | Get campaign details | Yes |
| GET | `/api/campaigns/stats/{id}` | Get campaign statistics | Yes |

### 🛍️ Order Management
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/orders` | List all orders | Yes |
| POST | `/api/orders` | Create new order | Yes |

### 🔔 Messaging & Delivery
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/vendor/send` | Send message via vendor | No (Public) |
| POST | `/api/delivery-receipt` | Delivery receipt webhook | No (Public) |

### 🧪 Health & Monitoring
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/actuator/health` | Application health status | No |

## 💻 Development Commands

### Build Project
```powershell
# Clean and build
.\mvnw.cmd clean install

# Build without tests
.\mvnw.cmd clean install -DskipTests

# Build with specific profile
.\mvnw.cmd clean install -Plocal
```

### Run Tests
```powershell
# Run all tests
.\mvnw.cmd test

# Run specific test class
.\mvnw.cmd test -Dtest=CrmBackendApplicationTests

# Run with coverage
.\mvnw.cmd test jacoco:report
```

### Package Application
```powershell
# Create JAR file
.\mvnw.cmd clean package

# Create JAR without tests
.\mvnw.cmd clean package -DskipTests

# JAR will be created in: target/crm_backend-0.0.1-SNAPSHOT.jar
```

### Run Packaged JAR
```powershell
# After packaging, run the JAR directly
java -jar target/crm_backend-0.0.1-SNAPSHOT.jar

# With environment variables
$env:MONGODB_URI = "your-connection-string"
$env:GOOGLE_CLIENT_ID = "your-client-id"
$env:GOOGLE_CLIENT_SECRET = "your-secret"
java -jar target/crm_backend-0.0.1-SNAPSHOT.jar
```

### Clean Build Artifacts
```powershell
# Clean target directory
.\mvnw.cmd clean

# Deep clean (including dependencies)
Remove-Item -Path target -Recurse -Force
```

## 🔧 Configuration

### Environment Variables

| Variable | Description | Required | Example |
|----------|-------------|----------|---------|
| `MONGODB_URI` | MongoDB connection string | Yes | `mongodb+srv://user:pass@cluster.mongodb.net/?appName=CRM` |
| `GOOGLE_CLIENT_ID` | Google OAuth Client ID | Yes | `123456-abc.apps.googleusercontent.com` |
| `GOOGLE_CLIENT_SECRET` | Google OAuth Client Secret | Yes | `GOCSPX-xxxxxxxxxxxxx` |
| `PORT` | Server port | No | `8080` (default) |
| `SPRING_PROFILES_ACTIVE` | Active profile | No | `local`, `test`, `prod` |

### Application Profiles

**1. Default Profile** (`application.properties`)
- Used for production
- Requires all environment variables
- CORS configured for production domains

**2. Local Profile** (`application-local.properties`)
- Used for local development
- Relaxed CORS settings
- Debug logging enabled

**3. Test Profile** (`application-test.properties`)
- Used for testing
- In-memory configurations
- Mock external services

**Running with profiles:**
```powershell
# Local profile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local

# Test profile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=test

# Production (default)
.\mvnw.cmd spring-boot:run
```

### CORS Configuration

Frontend domains are configured in `CorsConfig.java`:
```java
// Update this for your frontend URLs
configuration.setAllowedOrigins(Arrays.asList(
    "http://localhost:3000",
    "http://localhost:3001",
    "https://your-frontend-domain.com"
));
```

## 🐳 Docker Deployment

### Build Docker Image
```bash
# Build image
docker build -t crm-backend .

# Build with specific tag
docker build -t crm-backend:v1.0 .
```

### Run Docker Container
```bash
# Run with environment variables
docker run -p 8080:8080 \
  -e MONGODB_URI="mongodb+srv://user:pass@cluster.mongodb.net/?appName=CRM" \
  -e GOOGLE_CLIENT_ID="your-client-id" \
  -e GOOGLE_CLIENT_SECRET="your-secret" \
  crm-backend

# Run in detached mode
docker run -d -p 8080:8080 \
  -e MONGODB_URI="your-connection-string" \
  -e GOOGLE_CLIENT_ID="your-client-id" \
  -e GOOGLE_CLIENT_SECRET="your-secret" \
  --name crm-backend-container \
  crm-backend
```

### Docker Compose (Optional)
Create `docker-compose.yml`:
```yaml
version: '3.8'
services:
  backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      - MONGODB_URI=${MONGODB_URI}
      - GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID}
      - GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET}
```

Run with:
```bash
docker-compose up -d
```

## 🚨 Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running: `mongod`
- Check connection string format
- For Atlas, whitelist your IP address

### OAuth Errors
- Verify credentials in Google Cloud Console
- Check redirect URIs match your configuration
- Ensure OAuth consent screen is configured

### Port Already in Use
```powershell
# Kill process using port 8080
netstat -ano | findstr :8080
taskkill /PID <process-id> /F

# Or use different port
$env:PORT = "8081"
.\mvnw.cmd spring-boot:run
```

## 📁 Project Structure

```
CRM_BACKEND-main/
├── src/
│   ├── main/
│   │   ├── java/com/xeno/crm_backend/
│   │   │   ├── config/                    # Configuration classes
│   │   │   │   ├── CorsConfig.java        # CORS configuration
│   │   │   │   └── SecurityConfig.java    # Security & OAuth config
│   │   │   ├── controller/                # REST API endpoints
│   │   │   │   ├── CampaignController.java
│   │   │   │   ├── CustomerController.java
│   │   │   │   └── ...
│   │   │   ├── model/                     # Domain models
│   │   │   ├── repository/                # MongoDB repositories
│   │   │   └── pubsub/                    # Event system
│   │   └── resources/
│   │       ├── application.properties      # Default config
│   │       ├── application-local.properties
│   │       └── application-test.properties
│   └── test/                              # Test files
├── target/                                # Build output (gitignored)
├── mvnw.cmd                               # Maven wrapper (Windows)
├── pom.xml                                # Maven dependencies
├── Dockerfile                             # Docker configuration
├── .env.ps1                               # Environment variables (gitignored)
├── run-local.ps1                          # Local run script
└── README.md                              # This file
```

## 📚 Additional Documentation

- [MongoDB Setup Guide](MONGODB_SETUP.md)
- [Setup Guide](SETUP.md)
- [Status Document](STATUS.md)

## 📄 License

This project is for educational purposes.

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first.

## 🆘 Support

For questions or issues, open a [GitHub Issue](https://github.com/kinshukkush/CRM-BACKEND/issues).

---

**Built with ❤️ using Spring Boot and MongoDB**

**Repository:** https://github.com/kinshukkush/CRM-BACKEND
