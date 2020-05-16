FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*.jar hw5-api-gateway-auth.jar
EXPOSE 8001
CMD java -jar hw5-api-gateway-auth.jar