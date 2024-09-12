FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar statsApp.jar
ENTRYPOINT ["java","-jar","/statsApp.jar"]