FROM openjdk:21-slim

WORKDIR /workdir

COPY target/api-restaurant-0.0.1-SNAPSHOT.jar /workdir/api-restaurant.jar

EXPOSE 8080

CMD ["java", "-jar", "api-restaurant.jar"]