FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*.jar hw10-saga-delivery.jar
EXPOSE 8006
CMD java -jar hw10-saga-delivery.jar