FROM openjdk:8-jre-alpine
COPY build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT exec java -jar /app.jar -Xdebug -Xrunjdwp:transport=dt_socket,address=1997,server=y,suspend=n
