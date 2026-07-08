FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . .
RUN mvn -pl customer-service -am clean package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/customer-service/target/customer-service-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
