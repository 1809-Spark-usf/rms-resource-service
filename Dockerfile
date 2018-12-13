FROM openjdk:8-jdk-alpine
COPY target/RMSResourceService-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/RMSResourceService-0.0.1-SNAPSHOT.jar"]