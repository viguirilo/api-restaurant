FROM openjdk:21-slim

WORKDIR /workdir

ARG JAR_FILE

COPY target/${JAR_FILE} /workdir/api-restaurant.jar

EXPOSE 8080

CMD ["java", "-jar", "api-restaurant.jar"]