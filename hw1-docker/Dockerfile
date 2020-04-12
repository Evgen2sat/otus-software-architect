FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*.jar hw1-docker.jar
EXPOSE 8000
CMD java -jar hw1-docker.jar