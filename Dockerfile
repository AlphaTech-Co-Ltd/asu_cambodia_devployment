# Stage 1: Build
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Build without tests and checks
RUN ./gradlew clean bootJar -x test -x check --no-daemon

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
