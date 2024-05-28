FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

COPY pom.xml /usr/src/app/
COPY src /usr/src/app/src

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /usr/src/app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","/app.jar"]