FROM eclipse-temurin:21-jre-alpine
WORKDIR /application


COPY target/raid-planner.jar .
COPY target/dependency ./lib/
COPY ./settings.properties .

ENV CLASSPATH=/app/lib/*

ENTRYPOINT ["java", "-jar", "raid-planner.jar"]