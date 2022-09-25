FROM openjdk:11
MAINTAINER bsf
COPY target/service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
