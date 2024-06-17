FROM openjdk:11-jre-slim

VOLUME /tmp

ARG JAR_FILE=build/libs/cicd-jenkins-1.0.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 5000