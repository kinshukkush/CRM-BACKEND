# Local development run script for CRM Backend
# ================================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  CRM Backend - Local Development" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if .env.ps1 exists for custom configuration
if (Test-Path ".env.ps1") {
    Write-Host "Loading environment variables from .env.ps1..." -ForegroundColor Green
    . .\.env.ps1
} else {
    Write-Host "No .env.ps1 file found. Using default configuration..." -ForegroundColor Yellow
    Write-Host ""
    
    # MongoDB URI - using local MongoDB or you can use MongoDB Atlas
    if (-not $env:MONGODB_URI) {
        $env:MONGODB_URI = "mongodb://localhost:27017/xeno_crm"
        Write-Host "⚠️  Using default MongoDB: mongodb://localhost:27017/xeno_crm" -ForegroundColor Yellow
    }
    
    # Google OAuth credentials - replace with your actual credentials
    if (-not $env:GOOGLE_CLIENT_ID) {
        $env:GOOGLE_CLIENT_ID = "your-google-client-id"
        Write-Host "⚠️  Google Client ID not set! OAuth will not work." -ForegroundColor Red
        Write-Host "   Set GOOGLE_CLIENT_ID environment variable or create .env.ps1" -ForegroundColor Red
    }
    
    if (-not $env:GOOGLE_CLIENT_SECRET) {
        $env:GOOGLE_CLIENT_SECRET = "your-google-client-secret"
        Write-Host "⚠️  Google Client Secret not set! OAuth will not work." -ForegroundColor Red
        Write-Host "   Set GOOGLE_CLIENT_SECRET environment variable or create .env.ps1" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "Configuration:" -ForegroundColor Cyan
Write-Host "  MongoDB URI: $env:MONGODB_URI" -ForegroundColor White
Write-Host "  Google OAuth: " -NoNewline -ForegroundColor White
if ($env:GOOGLE_CLIENT_ID -ne "your-google-client-id") {
    Write-Host "✓ Configured" -ForegroundColor Green
} else {
    Write-Host "✗ Not configured" -ForegroundColor Red
}
Write-Host ""

Write-Host "Prerequisites:" -ForegroundColor Cyan
Write-Host "  • Java 21 must be installed" -ForegroundColor White
Write-Host "  • MongoDB must be running (if using local)" -ForegroundColor White
Write-Host ""

# Check Java version
Write-Host "Checking Java version..." -ForegroundColor Cyan
try {
    $javaVersion = & java -version 2>&1 | Select-Object -First 1
    Write-Host "  $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "  ✗ Java not found! Please install Java 21." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Starting application..." -ForegroundColor Green
Write-Host "Press Ctrl+C to stop the server" -ForegroundColor Yellow
Write-Host ""
Write-Host "Once started, access the application at:" -ForegroundColor Cyan
Write-Host "  http://localhost:8080" -ForegroundColor White
Write-Host "  Health check: http://localhost:8080/actuator/health" -ForegroundColor White
Write-Host ""

# Run the application
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
