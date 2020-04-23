FROM openjdk:8-jre-alpine
COPY build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT exec java -jar /app.jar
