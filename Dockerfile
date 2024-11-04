# Étape 1 : Construire l'application avec Maven
FROM maven:3.8.8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l'application avec JDK 21
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/mmiplatform.jar /app/mmiplatform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/mmiplatform.jar"]
