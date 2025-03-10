FROM openjdk:21-slim

WORKDIR /workdir

ARG JAR_FILE

COPY target/api-restaurant-0.0.1-SNAPSHOT.jar /workdir/api-restaurant.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api-restaurant.jar"]