FROM gradle:8.5.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew :profile-svc:bootJar --no-daemon

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/profile-svc/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
