FROM temurin:17-jdk-alpine

WORKDIR /url-shortener-service

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]