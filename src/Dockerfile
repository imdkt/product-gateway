# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Expose port for Spring Boot
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/product-gateway-0.0.1-SNAPSHOT.jar"]
