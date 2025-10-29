# Quick Test Run Script
# This script will attempt to run the application and show what's needed

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  CRM Backend - Quick Test" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Checking prerequisites..." -ForegroundColor Yellow
Write-Host ""

# Check Java
Write-Host "✓ Java 21: " -NoNewline
try {
    $javaVersion = & java -version 2>&1 | Select-Object -First 1
    Write-Host "Installed - $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "NOT FOUND!" -ForegroundColor Red
    Write-Host "  Please install Java 21 from https://adoptium.net/" -ForegroundColor Red
    exit 1
}

# Check MongoDB
Write-Host "⚠ MongoDB: " -NoNewline
Write-Host "Please ensure MongoDB is running" -ForegroundColor Yellow
Write-Host "  • Local: Run 'mongod' in another terminal" -ForegroundColor White
Write-Host "  • Or use MongoDB Atlas cloud connection" -ForegroundColor White
Write-Host ""

Write-Host "Note about OAuth:" -ForegroundColor Yellow
Write-Host "  This test will run with placeholder OAuth credentials." -ForegroundColor White
Write-Host "  Some endpoints requiring authentication will not work." -ForegroundColor White
Write-Host "  Public endpoints will be accessible." -ForegroundColor White
Write-Host ""

Write-Host "Setting up test environment..." -ForegroundColor Cyan
$env:MONGODB_URI = "mongodb://localhost:27017/xeno_crm"
$env:GOOGLE_CLIENT_ID = "test-client-id"
$env:GOOGLE_CLIENT_SECRET = "test-client-secret"

Write-Host ""
Write-Host "Starting application in TEST mode..." -ForegroundColor Green
Write-Host "Press Ctrl+C to stop" -ForegroundColor Yellow
Write-Host ""
Write-Host "Once started, test these URLs:" -ForegroundColor Cyan
Write-Host "  Health Check: http://localhost:8080/actuator/health" -ForegroundColor White
Write-Host "  Login Page: http://localhost:8080/login" -ForegroundColor White
Write-Host ""

# Run with test profile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=test
