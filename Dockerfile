# Stage 1: Build the application
FROM gradle:8.8-jdk17-jammy AS build
# Set working directory
WORKDIR /app

# Copy gradle files

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew build --no-daemon -x test -x check || return 0

# Copy source code
COPY src ./src

# Build application
RUN ./gradlew build --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]