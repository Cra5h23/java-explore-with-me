FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar mainApp.jar
ENTRYPOINT ["java","-jar","/mainApp.jar"]