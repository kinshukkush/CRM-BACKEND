# CRM Backend

A Spring Boot-based CRM (Customer Relationship Management) backend with MongoDB and Google OAuth authentication.

## Features

- 🔐 Google OAuth 2.0 Authentication
- 📊 Customer Management
- 📧 Campaign Management
- 🛍️ Order Tracking
- 💬 Communication Logs
- 🔔 Delivery Event System (Pub/Sub)
- 🌐 CORS Support for Frontend Integration

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.0**
- **MongoDB** (Database)
- **Spring Security** (with OAuth2)
- **Maven** (Build Tool)

## Quick Start

### Prerequisites

1. **Java 21** - [Download](https://adoptium.net/)
2. **MongoDB** - Local or [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
3. **Google OAuth Credentials** - [Setup Guide](https://console.cloud.google.com/)

### Setup & Run

1. **Clone the repository**
   ```bash
   cd c:\Users\kinsh\Downloads\CRM_BACKEND-main\CRM_BACKEND-main
   ```

2. **Configure environment variables**
   - Copy `.env.ps1.template` to `.env.ps1`
   - Update with your MongoDB URI and Google OAuth credentials:
   ```powershell
   $env:MONGODB_URI = "mongodb://localhost:27017/xeno_crm"
   $env:GOOGLE_CLIENT_ID = "your-client-id"
   $env:GOOGLE_CLIENT_SECRET = "your-client-secret"
   ```

3. **Run the application**
   ```powershell
   .\run-local.ps1
   ```
   
   Or manually:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. **Access the application**
   - API: http://localhost:8080
   - Health Check: http://localhost:8080/actuator/health

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