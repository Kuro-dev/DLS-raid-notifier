#TODO: Change this so that the mvn build also is executed in here, so that the dockerfile builds the project
#currently "mvn clean install" has to be run *before* docker compose.
FROM eclipse-temurin:21-jre-alpine
WORKDIR /application


COPY target/raid-planner.jar .
COPY target/dependency ./lib/
COPY ./settings.properties .

ENV CLASSPATH=/app/lib/*

ENTRYPOINT ["java", "-jar", "raid-planner.jar"]