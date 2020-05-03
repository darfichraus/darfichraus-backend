FROM openjdk:8-jre-alpine
COPY build/libs/*.jar app.jar
EXPOSE 8082
EXPOSE 1997
ENTRYPOINT exec java -jar /app.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1997
