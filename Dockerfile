FROM openjdk:8-jdk-alpine
COPY target/RMSResourceService-0.0.1-SNAPSHOT.jar tmp/RMSResourceService-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/tmp/RMSResourceService-0.0.1-SNAPSHOT.jar", "--server.servlet.context-path=/rms-resource" ,"&"]
