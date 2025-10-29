# CRM Backend - Setup and Run Guide

## Prerequisites
1. **Java 21** - Make sure Java 21 is installed
2. **MongoDB** - Either local installation or MongoDB Atlas account
3. **Google OAuth Credentials** - For authentication

## Quick Start

### 1. Setup MongoDB

#### Option A: Local MongoDB
- Install MongoDB Community Edition from https://www.mongodb.com/try/download/community
- Start MongoDB service:
  ```
  mongod
  ```
- MongoDB will be available at: `mongodb://localhost:27017`

#### Option B: MongoDB Atlas (Cloud)
- Create a free account at https://www.mongodb.com/cloud/atlas
- Create a cluster
- Get your connection string (format: `mongodb+srv://username:password@cluster.mongodb.net/`)
- Whitelist your IP address in Atlas security settings

### 2. Setup Google OAuth

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing one
3. Enable Google+ API
4. Go to "Credentials" → "Create Credentials" → "OAuth 2.0 Client ID"
5. Configure consent screen if needed
6. Add authorized redirect URIs:
   - `http://localhost:8080/login/oauth2/code/google` (for local development)
   - Your production URL if deploying
7. Copy the Client ID and Client Secret

### 3. Configure Environment Variables

Create a `.env.ps1` file in the project root:

```powershell
$env:MONGODB_URI = "mongodb://localhost:27017/xeno_crm"
$env:GOOGLE_CLIENT_ID = "your-actual-client-id"
$env:GOOGLE_CLIENT_SECRET = "your-actual-client-secret"
```

### 4. Run the Application

#### Using the provided script:
```powershell
.\run-local.ps1
```

#### Or manually:
```powershell
# Set environment variables
$env:MONGODB_URI = "mongodb://localhost:27017/xeno_crm"
$env:GOOGLE_CLIENT_ID = "your-google-client-id"
$env:GOOGLE_CLIENT_SECRET = "your-google-client-secret"

# Run with Maven
.\mvnw.cmd spring-boot:run
```

#### Or with local profile:
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

### 5. Verify the Application

- Application should start on: http://localhost:8080
- Health check: http://localhost:8080/actuator/health
- Login endpoint: http://localhost:8080/login

## API Endpoints

- `GET /api/user` - Get current user info (authenticated)
- `GET /api/customers` - List customers (authenticated)
- `POST /api/customers` - Create customer (authenticated)
- `GET /api/campaigns` - List campaigns (authenticated)
- `POST /api/campaigns` - Create campaign (authenticated)
- `POST /api/orders` - Create order (authenticated)
- `POST /vendor/send` - Send message via vendor (public)
- `POST /api/delivery-receipt` - Delivery receipt webhook (public)

## Troubleshooting

### MongoDB Connection Issues
- **Error:** `MongoTimeoutException`
  - **Solution:** Make sure MongoDB is running: `mongod` or check Atlas connection string

### OAuth Issues
- **Error:** `invalid_client`
  - **Solution:** Verify Google Client ID and Secret are correct
  - **Solution:** Check redirect URI in Google Console matches your application URL

### Port Already in Use
- **Error:** `Port 8080 is already in use`
  - **Solution:** Change port in application.properties: `server.port=8081`
  - Or kill the process using port 8080

### Build Issues
- Run: `.\mvnw.cmd clean install -DskipTests`

## Testing Without OAuth (Development Mode)

If you want to test without setting up OAuth, you can temporarily modify `SecurityConfig.java` to permit all requests. See the commented section in the code.

## Docker Deployment

To run in Docker:
```bash
docker build -t crm-backend .
docker run -p 8080:8080 \
  -e MONGODB_URI="your-mongodb-uri" \
  -e GOOGLE_CLIENT_ID="your-client-id" \
  -e GOOGLE_CLIENT_SECRET="your-client-secret" \
  crm-backend
```
