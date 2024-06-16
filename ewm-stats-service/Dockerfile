FROM amazoncorretto:11
COPY wait-for-it.sh wait-for-it.sh
COPY target/*.jar app.jar
ENTRYPOINT ["./wait-for-it.sh"]
CMD ["java","-jar","/app.jar"]


#FROM amazoncorretto:11
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]