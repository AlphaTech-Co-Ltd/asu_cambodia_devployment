# Stage 1: Build
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Build only the Spring Boot JAR, skip tests/checks
RUN ./gradlew clean bootJar -x test -x check --no-daemon

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built JAR
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
