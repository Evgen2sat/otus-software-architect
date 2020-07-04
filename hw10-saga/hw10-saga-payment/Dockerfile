FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*.jar hw10-saga-payment.jar
EXPOSE 8004
CMD java -jar hw10-saga-payment.jar