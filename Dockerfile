FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . /app
RUN ./mvnw clean package -DskipTests

#CMD ["java", "-jar", "target/book-library-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "$(ls target/*.jar | head -n 1)"]