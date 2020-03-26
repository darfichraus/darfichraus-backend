FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests 


FROM openjdk:8-jdk-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
