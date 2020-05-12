FROM openjdk:11

MAINTAINER cbartram3@uravanapp.com

# Setup of some environmental variables
ENV ACTIVE_PROFILE local
ARG JAR_FILE=build/libs/*.jar

# Copies the built jar file into the docker image
COPY ${JAR_FILE} application.jar

# Expose the necessary ports
EXPOSE 8080
EXPOSE 27017

CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "application.jar"]
