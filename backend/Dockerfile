FROM openjdk:11.0.4-jre-slim
COPY ./target/backend-0.0.1.jar /opt/app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=default", "/opt/app.jar"]