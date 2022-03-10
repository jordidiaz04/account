FROM adoptopenjdk/openjdk8:alpine-slim
ADD target/accounts-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
EXPOSE 27020
ENTRYPOINT ["java", "-jar","/app.jar"]