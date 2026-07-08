FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . .
RUN mvn -pl api-gateway -am clean package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/api-gateway/target/api-gateway-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
