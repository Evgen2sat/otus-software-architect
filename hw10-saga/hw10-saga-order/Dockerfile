FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*.jar hw10-saga-order.jar
EXPOSE 8003
CMD java -jar hw10-saga-order.jar