FROM openjdk:8-jdk-alpine
ADD /target/darfichraus*.jar app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
