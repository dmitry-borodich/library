FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY target/library-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080