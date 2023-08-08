FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080
COPY target/CloudStorage.jar .


ENTRYPOINT ["java", "-jar", "CloudStorage.jar"]



