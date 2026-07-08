# Architecture/Tech: Multi-stage Docker build for Spring Boot WebFlux payment-service.
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . .
RUN mvn -pl payment-service -am clean package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/payment-service/target/payment-service-1.0.0.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","/app/app.jar"]
