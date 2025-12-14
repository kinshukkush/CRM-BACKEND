# Security Policy

## 🔒 Security Overview

The CRM Backend implements enterprise-grade security measures to protect data and ensure safe operations.

## 🛡️ Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## 🔐 Security Features

### 1. Authentication & Authorization

**Google OAuth 2.0**
- Secure third-party authentication
- Industry-standard OAuth 2.0 flow
- JWT token-based sessions
- Automatic token validation

**Spring Security**
- Method-level security
- Role-based access control
- CSRF protection
- Session management

### 2. Data Protection

**Database Security**
- MongoDB Atlas encryption at rest
- TLS/SSL for data in transit
- Database access controls
- Connection string encryption

**API Security**
- CORS configuration
- Input validation
- Output sanitization
- Rate limiting (recommended)

### 3. Authentication Flow

```
User → Frontend → Backend OAuth Endpoint
     → Google OAuth → Authorization Code
     → Backend Validates → JWT Token
     → Frontend Receives Token
```

**Security Measures:**
- State parameter for CSRF
- Nonce for replay attacks
- Secure redirect validation
- Token expiration

## 🚨 Reporting Vulnerabilities

### Where to Report

**DO NOT** create public issues for security vulnerabilities.

Report via:
1. **Email**: security@yourcompany.com
2. **Private Security Advisory** on GitHub

### What to Include

```
Subject: [SECURITY] Brief description

Details:
- Vulnerability type
- Affected components
- Steps to reproduce
- Potential impact
- Suggested fix (optional)
- Your contact info
```

### Response Timeline

- **24 hours**: Acknowledgment
- **72 hours**: Initial assessment
- **7 days**: Detailed response
- **30 days**: Security patch (if needed)

## 🔍 Security Best Practices

### For Developers

1. **Environment Variables**
   ```bash
   # ✅ Good: Use environment variables
   MONGODB_URI=mongodb+srv://...
   GOOGLE_CLIENT_SECRET=secret
   
   # ❌ Bad: Hardcoded in application.properties
   mongodb.uri=mongodb+srv://user:pass@...
   ```

2. **Password Security**
   ```java
   // ✅ Good: Never log passwords
   log.info("User {} logged in", userId);
   
   // ❌ Bad: Logging sensitive data
   log.info("Login: {}", userCredentials);
   ```

3. **SQL/NoSQL Injection Prevention**
   ```java
   // ✅ Good: Use repository methods
   customerRepository.findByEmail(email);
   
   // ✅ Good: Use Query with Criteria
   Query query = new Query(Criteria.where("email").is(email));
   
   // ❌ Bad: String concatenation
   String query = "db.customers.find({email: '" + email + "'})";
   ```

4. **Error Handling**
   ```java
   // ✅ Good: Generic error messages
   catch (Exception e) {
       log.error("Operation failed", e);
       return ResponseEntity.status(500)
           .body(Map.of("error", "An error occurred"));
   }
   
   // ❌ Bad: Exposing stack traces
   catch (Exception e) {
       return ResponseEntity.status(500).body(e.toString());
   }
   ```

## 🛡️ Common Vulnerabilities & Mitigations

### 1. Injection Attacks

**Protection:**
- Use Spring Data repositories
- Parameterized queries
- Input validation with `@Valid`
- MongoDB Criteria API

### 2. Broken Authentication

**Protection:**
- OAuth 2.0 implementation
- JWT with expiration
- Secure session management
- Proper logout handling

### 3. Sensitive Data Exposure

**Protection:**
- Environment variables for secrets
- No credentials in logs
- HTTPS enforcement
- Encrypted database connections

### 4. XML External Entities (XXE)

**Protection:**
- JSON preferred over XML
- Disable XML external entity processing
- Validate all input

### 5. Broken Access Control

**Protection:**
```java
@PreAuthorize("isAuthenticated()")
@GetMapping("/api/admin")
public String adminOnly() {
    // Protected endpoint
}
```

### 6. Security Misconfiguration

**Protection:**
- Disable default credentials
- Remove unused dependencies
- Configure security headers
- Regular dependency updates

## 🔒 Configuration Security

### application.properties

```properties
# Security headers
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.same-site=strict

# Disable unnecessary features
spring.jmx.enabled=false
management.endpoints.web.exposure.include=health,info

# Production settings
logging.level.root=INFO
spring.devtools.restart.enabled=false
```

### Security Headers

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .headers(headers -> headers
                .frameOptions().deny()
                .xssProtection().enable()
                .contentTypeOptions().enable()
            );
        return http.build();
    }
}
```

## 🔐 JWT Token Security

### Token Generation

```java
public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1800000)) // 30 min
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
}
```

### Token Validation

```java
public boolean validateToken(String token) {
    try {
        Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token);
        return true;
    } catch (JwtException e) {
        log.error("Invalid JWT token", e);
        return false;
    }
}
```

## 📋 Security Checklist

### Production Deployment

- [ ] All secrets in environment variables
- [ ] HTTPS enabled
- [ ] CORS properly configured
- [ ] Database access restricted
- [ ] Security headers configured
- [ ] Error messages sanitized
- [ ] Logging configured (no sensitive data)
- [ ] Dependencies updated
- [ ] Security scan completed
- [ ] Rate limiting implemented

### Development

- [ ] .env files not committed
- [ ] No hardcoded credentials
- [ ] Test data anonymized
- [ ] Debug logging disabled
- [ ] Security tests written

## 🔧 Dependency Management

### Check for Vulnerabilities

```bash
# Maven security check
.\mvnw.cmd dependency-check:check

# Update dependencies
.\mvnw.cmd versions:display-dependency-updates

# OWASP Dependency Check (if configured)
.\mvnw.cmd org.owasp:dependency-check-maven:check
```

### Keep Dependencies Updated

```xml
<!-- pom.xml -->
<dependencies>
    <!-- Always use latest stable versions -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>${spring-boot.version}</version>
    </dependency>
</dependencies>
```

## 🔐 Database Security

### MongoDB Atlas Security

1. **Network Access**
   - Whitelist specific IPs only
   - Use VPN for production access
   - No 0.0.0.0/0 in production

2. **Database Access**
   - Unique credentials per environment
   - Principle of least privilege
   - Regular password rotation

3. **Encryption**
   - Enable encryption at rest
   - Use TLS/SSL connections
   - Secure connection strings

### Connection String Security

```bash
# ✅ Good: Environment variable
MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/

# ❌ Bad: Hardcoded in code
String uri = "mongodb+srv://user:pass@cluster.mongodb.net/";
```

## 🚀 Security Updates

### Update Process

1. Vulnerability identified
2. Patch developed and tested
3. Security advisory published
4. Critical users notified
5. Update deployed

### Stay Updated

```bash
# Check for security updates
.\mvnw.cmd versions:display-dependency-updates

# Update Spring Boot
# Update version in pom.xml and rebuild
```

## 📞 Security Contact

- **Email**: security@yourcompany.com
- **Response Time**: 24-48 hours
- **Emergency**: Tag [URGENT]

## 📚 Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security](https://spring.io/projects/spring-security)
- [MongoDB Security](https://docs.mongodb.com/manual/security/)
- [OAuth 2.0](https://oauth.net/2/)

## 🏅 Security Hall of Fame

We thank security researchers who responsibly disclose vulnerabilities:

- *Be the first contributor!*

---

Last Updated: December 14, 2025

Thank you for helping keep CRM Backend secure! 🔒
