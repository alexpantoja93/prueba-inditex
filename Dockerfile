FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

COPY --from=builder /app/target/prueba-inditex-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]