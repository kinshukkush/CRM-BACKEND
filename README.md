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

## API Endpoints

### Authentication
- `GET /login` - Initiate Google OAuth login
- `GET /logout` - Logout

### Customer Management
- `GET /api/customers` - List all customers
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Campaign Management
- `GET /api/campaigns` - List campaigns
- `POST /api/campaigns` - Create campaign
- `GET /api/campaigns/{id}` - Get campaign details

### Order Management
- `POST /api/orders` - Create order
- `GET /api/orders` - List orders

### Other
- `POST /vendor/send` - Send message via vendor (public endpoint)
- `POST /api/delivery-receipt` - Delivery receipt webhook (public)
- `GET /api/user` - Get current authenticated user

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `MONGODB_URI` | MongoDB connection string | `mongodb://localhost:27017/xeno_crm` |
| `GOOGLE_CLIENT_ID` | Google OAuth Client ID | Required |
| `GOOGLE_CLIENT_SECRET` | Google OAuth Client Secret | Required |
| `PORT` | Server port | `8080` |

### Application Profiles

- **default** - Production configuration
- **local** - Local development configuration

Run with profile:
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

## Development

### Build Project
```powershell
.\mvnw.cmd clean install
```

### Run Tests
```powershell
.\mvnw.cmd test
```

### Package JAR
```powershell
.\mvnw.cmd clean package
```

## Docker Deployment

Build and run with Docker:

```bash
docker build -t crm-backend .
docker run -p 8080:8080 \
  -e MONGODB_URI="your-mongodb-uri" \
  -e GOOGLE_CLIENT_ID="your-client-id" \
  -e GOOGLE_CLIENT_SECRET="your-client-secret" \
  crm-backend
```

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running: `mongod`
- Check connection string format
- For Atlas, whitelist your IP address

### OAuth Errors
- Verify credentials in Google Cloud Console
- Check redirect URIs match your configuration
- Ensure OAuth consent screen is configured

### Port Already in Use
Change port in application.properties or set `PORT` environment variable

## Documentation

For detailed setup instructions, see [SETUP.md](SETUP.md)

## Project Structure

```
src/
├── main/
│   ├── java/com/xeno/crm_backend/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST API controllers
│   │   ├── model/          # Domain models
│   │   ├── repository/     # MongoDB repositories
│   │   └── pubsub/         # Event system
│   └── resources/
│       └── application.properties
└── test/
    └── java/               # Test classes
```

## License

This project is for educational purposes.

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## Support

For questions or issues, please open a GitHub issue.
