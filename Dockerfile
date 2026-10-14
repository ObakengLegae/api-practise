FROM eclipse-temurin:21-jre-alpine as api

WORKDIR /app

COPY target/api-practise-1.0-SNAPSHOT.jar /app/api-practise.jar

EXPOSE 7000

CMD ["java", "jar", "api-practise.jar"]