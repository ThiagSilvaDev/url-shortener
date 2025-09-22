# Stage 1: Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies to leverage Docker layer caching
COPY pom.xml .

RUN mvn dependency:go-offline

# Copy the rest of the source code and build the project
COPY ../src ./src

RUN mvn clean package -DskipTests

# Stage 2: Final stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create a non-root user for security
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

USER appuser

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]