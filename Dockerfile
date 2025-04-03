FROM openjdk:11-jdk

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY application-prod.properties application-prod.properties

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.additional-location=optional:file:./"]