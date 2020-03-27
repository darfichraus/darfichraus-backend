FROM openjdk:8-jre-alpine
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT exec java -jar /app.jar
