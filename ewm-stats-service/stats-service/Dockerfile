FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar statsApp.jar
#EXPOSE 9090
ENTRYPOINT ["java","-jar","/statsApp.jar"]