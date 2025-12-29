# Step 1: Build stage
FROM gradle:8.10-jdk17 AS build
WORKDIR /app

# Copy Gradle configuration files first
COPY build.gradle settings.gradle ./
COPY gradlew ./
COPY gradle ./gradle

# Download dependencies
RUN ./gradlew dependencies --no-daemon || return 0

# Copy source code
COPY src ./src

# Build the JAR file
RUN ./gradlew clean bootJar --no-daemon

# Step 2: Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /app/build/libs/*.jar app.jar

ENV PORT=8000
EXPOSE 8000

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]