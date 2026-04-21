# Étape 1 : Construction
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/scilib-devops-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
