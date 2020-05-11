FROM openjdk:11

COPY . /application/

WORKDIR /application/

RUN ./gradlew build

CMD ["java", "-jar", "./build/libs/HealthConsumer-0.0.1-SNAPSHOT.jar"]
