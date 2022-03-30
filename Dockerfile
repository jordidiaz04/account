FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 8081
ADD target/accounts.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]