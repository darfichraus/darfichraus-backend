FROM openjdk:8-jre
ADD /target/darfichraus*.jar app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar