FROM 115473357299.dkr.ecr.eu-west-1.amazonaws.com/eclipse-temurin:17-jre-alpine
VOLUME /tmp
ADD target/teams-notification-poc-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
