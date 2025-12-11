FROM amazoncorretto:23.0.2

WORKDIR /app

COPY target/tracking-money-flow.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
