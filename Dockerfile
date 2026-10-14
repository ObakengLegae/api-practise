FROM eclipse-temurin:21-jre-alpine AS api

WORKDIR /app

COPY target/api-practise-1.0-SNAPSHOT.jar /app/api-practise.jar

EXPOSE 7000

ENTRYPOINT ["java", "-jar", "api-practise.jar"]