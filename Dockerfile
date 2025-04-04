# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Online-Food-Ordering-0.0.1-SNAPSHOT.jar quickBite.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "quickBite.jar"]
