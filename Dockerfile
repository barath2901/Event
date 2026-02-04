# --- STAGE 1: BUILD ---
# Use a Maven image with Java 21 to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the source code and pom.xml
COPY . .

# Build the application (skipping tests to speed up deployment)
RUN mvn clean package -DskipTests

# --- STAGE 2: RUN ---
# Use a lightweight Java 21 image for the final application
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (The standard Spring Boot port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]