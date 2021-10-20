From java:8
EXPOSE 8780
ADD /target/go2shop-auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ADD /target/classes/jwt.jks jwt.jks
ENTRYPOINT ["java", "-jar","/auth-service.jar"]