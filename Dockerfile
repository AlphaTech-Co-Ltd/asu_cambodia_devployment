# -------- Build stage --------
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy build files
COPY gradlew .
COPY gradle gradle
COPY . .

# Build without running tests
RUN chmod +x gradlew
RUN ./gradlew clean build -x test --no-daemon

# -------- Run stage --------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy JAR from build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose default port (fallback for local runs)
EXPOSE 8080

# Pass the Railway-assigned port into Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:-8080}"]
