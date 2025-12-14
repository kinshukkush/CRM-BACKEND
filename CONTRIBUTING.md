# Contributing to CRM Backend

Thank you for contributing to the CRM Backend! This guide will help you get started.

## 🎯 How to Contribute

### 1. Reporting Bugs 🐛
- Check existing issues first
- Use bug report template
- Include error logs and stack traces
- Specify Java version and environment

### 2. Suggesting Features ✨
- Describe the feature clearly
- Explain the use case
- Consider API design
- Think about database impact

### 3. Code Contributions 💻
- Fork the repository
- Create feature branch
- Write clean, tested code
- Submit pull request

## 🚀 Development Setup

### Prerequisites

```bash
# Check Java version (must be 21)
java -version

# Verify Maven
.\mvnw.cmd --version
```

### Local Development

```bash
# 1. Clone the repository
git clone https://github.com/YOUR-USERNAME/CRM-BACKEND.git
cd CRM-BACKEND

# 2. Set environment variables
$env:MONGODB_URI = "mongodb+srv://..."
$env:GOOGLE_CLIENT_ID = "your-client-id"
$env:GOOGLE_CLIENT_SECRET = "your-secret"

# 3. Run the application
.\mvnw.cmd spring-boot:run

# 4. Test endpoint
curl http://localhost:8080/actuator/health
```

## 📁 Project Structure

```
src/main/java/com/xeno/crm_backend/
├── CrmBackendApplication.java    # Main application
├── config/
│   ├── CorsConfig.java           # CORS configuration
│   ├── SecurityConfig.java       # Security & OAuth
│   ├── JwtAuthenticationFilter.java
│   └── OAuth2SuccessHandler.java
├── controller/
│   ├── CampaignController.java
│   ├── CustomerController.java
│   ├── CustomerFilterController.java
│   ├── OrderController.java
│   └── UserController.java
├── model/
│   ├── Campaign.java
│   ├── Customer.java
│   ├── Order.java
│   ├── CommunicationLog.java
│   └── User.java
├── repository/
│   ├── CampaignRepository.java
│   ├── CustomerRepository.java
│   ├── OrderRepository.java
│   └── CommunicationLogRepository.java
└── pubsub/
    └── DeliveryReceiptController.java
```

## 📝 Coding Standards

### Java Style Guide

```java
// Good ✅
@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    
    @Autowired
    private CampaignRepository campaignRepository;
    
    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }
    
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign saved = campaignRepository.save(campaign);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("Failed to create campaign", e);
            return ResponseEntity.status(500).build();
        }
    }
}

// Avoid ❌
@RestController
public class Controller {
    @Autowired Repository repo;
    
    @GetMapping("/api/c")
    public List get() {
        return repo.findAll();
    }
}
```

### Best Practices

1. **Naming Conventions**
   - Classes: PascalCase (`CustomerController`)
   - Methods: camelCase (`findCustomerById`)
   - Constants: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`)

2. **Error Handling**
   ```java
   try {
       // Operation
   } catch (SpecificException e) {
       log.error("Error message", e);
       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
           .body(Map.of("error", "User-friendly message"));
   }
   ```

3. **Logging**
   ```java
   private static final Logger log = LoggerFactory.getLogger(ClassName.class);
   
   log.info("User {} logged in", userId);
   log.error("Failed to process order {}", orderId, exception);
   ```

4. **Database Queries**
   ```java
   // Use repository methods
   List<Customer> customers = customerRepository.findByTotalSpendGreaterThan(1000);
   
   // Use MongoTemplate for complex queries
   Query query = new Query();
   query.addCriteria(Criteria.where("totalSpend").gt(value));
   List<Customer> results = mongoTemplate.find(query, Customer.class);
   ```

## 🧪 Testing

### Run Tests

```bash
# Run all tests
.\mvnw.cmd test

# Run specific test class
.\mvnw.cmd test -Dtest=CustomerControllerTest

# Run with coverage
.\mvnw.cmd test jacoco:report
```

### Writing Tests

```java
@SpringBootTest
class CustomerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    void testCreateCustomer() throws Exception {
        String customerJson = "{\"name\":\"John\",\"email\":\"john@example.com\"}";
        
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"));
    }
}
```

## 📦 Pull Request Process

### PR Title Format

```
[Type] Brief description

Examples:
[Feature] Add customer export endpoint
[Fix] Resolve null pointer in campaign delivery
[Docs] Update API documentation
[Refactor] Improve error handling in controllers
```

### PR Description Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Comments added for complex logic
- [ ] Documentation updated
- [ ] No new warnings
- [ ] Tests pass locally
```

## 🔧 Database Management

### MongoDB Atlas Setup

1. **Create Cluster** (if not exists)
2. **Database Access**: Create user with read/write permissions
3. **Network Access**: Add your IP address (0.0.0.0/0 for development)
4. **Get Connection String**: Copy from Atlas dashboard

### Common MongoDB Operations

```java
// Insert
Customer customer = new Customer("John", "john@example.com");
customerRepository.save(customer);

// Find
List<Customer> customers = customerRepository.findAll();
Optional<Customer> customer = customerRepository.findById(id);

// Update
customer.setName("John Updated");
customerRepository.save(customer);

// Delete
customerRepository.deleteById(id);

// Complex Query
Query query = new Query();
query.addCriteria(Criteria.where("totalSpend").gt(1000)
    .and("visits").lt(5));
List<Customer> results = mongoTemplate.find(query, Customer.class);
```

## 🔒 Security Guidelines

### Authentication

```java
// Protect endpoints
@PreAuthorize("isAuthenticated()")
@GetMapping("/api/protected")
public String protectedResource() {
    return "Protected data";
}

// Get authenticated user
@GetMapping("/api/user")
public User getCurrentUser(Principal principal) {
    return userService.findByEmail(principal.getName());
}
```

### Input Validation

```java
@PostMapping("/api/customers")
public ResponseEntity<Customer> createCustomer(
    @Valid @RequestBody Customer customer) {
    // @Valid triggers validation
    return ResponseEntity.ok(customerRepository.save(customer));
}

// Model validation
public class Customer {
    @NotNull
    @Size(min = 2, max = 100)
    private String name;
    
    @Email
    private String email;
    
    @Min(0)
    private Double totalSpend;
}
```

## 🎨 API Design Guidelines

### REST Best Practices

```java
// Good ✅
GET    /api/customers           // List all
GET    /api/customers/{id}      // Get one
POST   /api/customers           // Create
PUT    /api/customers/{id}      // Update
DELETE /api/customers/{id}      // Delete
POST   /api/customers/filter    // Custom query

// Avoid ❌
GET /api/getCustomers
POST /api/createCustomer
GET /api/customer-by-id
```

### Response Format

```java
// Success
return ResponseEntity.ok(data);

// Created
return ResponseEntity.status(HttpStatus.CREATED).body(created);

// Error
return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    .body(Map.of("error", "Message", "code", "ERROR_CODE"));

// Not Found
return ResponseEntity.notFound().build();
```

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security OAuth](https://spring.io/projects/spring-security-oauth)
- [MongoDB Java Driver](https://mongodb.github.io/mongo-java-driver/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 🔍 Code Review Checklist

- [ ] Code compiles without errors
- [ ] Tests pass
- [ ] No security vulnerabilities
- [ ] Proper error handling
- [ ] Logging added
- [ ] API documented
- [ ] Database indexes considered
- [ ] Performance tested

## 🏆 Recognition

Contributors will be listed in:
- CONTRIBUTORS.md
- Release notes
- Documentation credits

## 📞 Getting Help

- **Documentation**: Check existing docs
- **Issues**: Search for similar issues
- **Discussions**: Ask questions
- **Email**: support@yourcompany.com

---

Thank you for contributing! 🎉
