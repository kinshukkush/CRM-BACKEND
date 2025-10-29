# MongoDB Setup Guide

## Option 1: Local MongoDB Installation (Recommended for Development)

### Step 1: Download MongoDB
1. Go to: https://www.mongodb.com/try/download/community
2. Download MongoDB Community Edition for Windows
3. Run the installer (.msi file)

### Step 2: Install MongoDB
1. Choose "Complete" installation
2. Install MongoDB as a Service (check the box)
3. Install MongoDB Compass (optional GUI tool - recommended)
4. Complete the installation

### Step 3: Verify Installation
Open PowerShell and run:
```powershell
# Check if MongoDB is running
Get-Service MongoDB

# Or check the status
mongod --version
```

### Step 4: Start MongoDB (if not running)
```powershell
# Start MongoDB service
Start-Service MongoDB

# Or if installed without service, run manually:
mongod --dbpath "C:\data\db"
```

### Step 5: Test Connection
```powershell
# Connect to MongoDB shell
mongosh

# Or use the old mongo shell
mongo
```

### Step 6: Configure Your Application
Your application is already configured to connect to `mongodb://localhost:27017/xeno_crm`

Just run:
```powershell
.\test-run.ps1
```

---

## Option 2: MongoDB Atlas (Cloud - Free Tier)

### Step 1: Create Account
1. Go to: https://www.mongodb.com/cloud/atlas/register
2. Sign up for a free account
3. Verify your email

### Step 2: Create a Cluster
1. Click "Build a Database"
2. Choose "M0 Free" tier
3. Select a cloud provider and region (closest to you)
4. Click "Create Cluster" (takes 3-5 minutes)

### Step 3: Create Database User
1. Click "Database Access" in left sidebar
2. Click "Add New Database User"
3. Choose "Password" authentication
4. Username: `crm_user`
5. Password: Create a strong password (save it!)
6. Database User Privileges: "Read and write to any database"
7. Click "Add User"

### Step 4: Whitelist Your IP Address
1. Click "Network Access" in left sidebar
2. Click "Add IP Address"
3. Click "Add Current IP Address" (or "Allow Access from Anywhere" for testing)
4. Click "Confirm"

### Step 5: Get Connection String
1. Go back to "Database" (main page)
2. Click "Connect" on your cluster
3. Choose "Connect your application"
4. Copy the connection string (looks like):
   ```
   mongodb+srv://crm_user:<password>@cluster0.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```

### Step 6: Configure Connection String
Replace `<password>` with your actual password:
```
mongodb+srv://crm_user:YourPassword123@cluster0.xxxxx.mongodb.net/xeno_crm?retryWrites=true&w=majority
```

### Step 7: Update Your Application

**Method A: Using .env.ps1 file**
```powershell
# Create .env.ps1 file
Copy-Item .env.ps1.template .env.ps1

# Edit .env.ps1 and update the MongoDB URI:
$env:MONGODB_URI = "mongodb+srv://crm_user:YourPassword123@cluster0.xxxxx.mongodb.net/xeno_crm?retryWrites=true&w=majority"
```

**Method B: Direct environment variable**
```powershell
$env:MONGODB_URI = "mongodb+srv://crm_user:YourPassword123@cluster0.xxxxx.mongodb.net/xeno_crm?retryWrites=true&w=majority"
.\mvnw.cmd spring-boot:run
```

---

## Option 3: Docker MongoDB (For Testing)

### Run MongoDB in Docker
```powershell
# Pull MongoDB image
docker pull mongo

# Run MongoDB container
docker run -d -p 27017:27017 --name mongodb mongo

# Check if running
docker ps

# Stop MongoDB
docker stop mongodb

# Start MongoDB again
docker start mongodb
```

Your application will connect to `mongodb://localhost:27017/xeno_crm`

---

## Quick Test: Check if MongoDB is Accessible

### For Local MongoDB:
```powershell
# Test connection
Test-NetConnection -ComputerName localhost -Port 27017
```

### For MongoDB Atlas:
Make sure you:
1. Whitelisted your IP address
2. Created a database user
3. Used the correct connection string with password

---

## Troubleshooting

### Issue: "Connection refused" or "MongoTimeoutException"

**For Local MongoDB:**
- Check if MongoDB service is running:
  ```powershell
  Get-Service MongoDB
  ```
- Start the service:
  ```powershell
  Start-Service MongoDB
  ```

**For MongoDB Atlas:**
- Verify IP address is whitelisted
- Check username/password in connection string
- Ensure no firewall blocking port 27017 or 27017

### Issue: "Authentication failed"

**For Local MongoDB:**
- Local MongoDB doesn't require auth by default
- Use: `mongodb://localhost:27017/xeno_crm`

**For MongoDB Atlas:**
- Double-check username and password
- Make sure password doesn't contain special characters (or URL encode them)
- Verify user has correct permissions

### Issue: MongoDB service won't start
```powershell
# Check logs
Get-EventLog -LogName Application -Source MongoDB -Newest 10

# Or manually start with logging
mongod --dbpath "C:\data\db" --logpath "C:\data\log\mongodb.log"
```

---

## Recommended Setup for Your Project

**For Development:**
- Use **Local MongoDB** (faster, no internet required)
- Connection: `mongodb://localhost:27017/xeno_crm`

**For Production/Deployment:**
- Use **MongoDB Atlas** (reliable, managed, scalable)
- Connection: Your Atlas connection string

---

## Current Configuration Files

Your project already has these connection configurations:

1. **Default** (application.properties):
   ```
   spring.data.mongodb.uri=${MONGODB_URI}
   ```

2. **Local** (application-local.properties):
   ```
   spring.data.mongodb.uri=${MONGODB_URI:mongodb://localhost:27017/xeno_crm}
   ```

3. **Test** (application-test.properties):
   ```
   spring.data.mongodb.uri=mongodb://localhost:27017/xeno_crm_test
   ```

Just set the `MONGODB_URI` environment variable to your connection string!
