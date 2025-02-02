FROM openjdk:17
WORKDIR /app
COPY data.csv /app/data.csv
COPY target/swift-codes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
