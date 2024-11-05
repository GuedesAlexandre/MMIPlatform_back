FROM maven:3.8.8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/mmiplatform.jar /app/mmiplatform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/mmiplatform.jar"]
