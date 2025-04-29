# Build stage
FROM gradle:8.5.0-jdk21 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle ../ ..
RUN gradle :store-of-value-svc:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /home/gradle/project/store-of-value-svc/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
