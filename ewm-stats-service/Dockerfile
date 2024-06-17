FROM amazoncorretto:11
COPY target/*.jar statsApp.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/statsApp.jar"]