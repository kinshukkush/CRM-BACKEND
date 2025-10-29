# Use a lightweight JDK image
FROM eclipse-temurin:21-jdk-alpine

# Create and switch to /app
WORKDIR /app

# Copy everything into the container
COPY . ./

# Make the Maven wrapper executable (if not already)
RUN chmod +x mvnw

# Build the JAR (skip tests for faster deploy)
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (Spring Boot’s default)
EXPOSE 8080

# At runtime, launch the generated JAR
CMD ["sh", "-c", "java -jar target/*.jar"]
