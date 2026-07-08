# Banking Reactive Platform

A GitHub-ready sample platform showing **Spring Boot + WebFlux + Kafka + WebClient + Resilience4j Circuit Breaker** in a banking-style loan workflow.

## What this project demonstrates

- **Reactive REST APIs** with Spring WebFlux
- **Service-to-service calls** with Spring WebClient
- **Event-driven architecture** with Kafka
- **Fault tolerance** with Resilience4j Circuit Breaker
- **API Gateway** using Spring Cloud Gateway
- **Docker Compose** with Kafka + ZooKeeper + 5 microservices
- **HLD / LLD diagrams** in Mermaid for GitHub rendering

## Business flow

1. Client calls **API Gateway**
2. Gateway forwards request to **Loan Service**
3. Loan Service calls **Customer Service** via WebClient
4. Circuit breaker protects the Customer Service call
5. Loan Service publishes `loan-events` to Kafka
6. Risk Service consumes the event and publishes `loan-decisions`
7. Notification Service consumes `loan-decisions`

## Architecture diagrams

See [architecture documentation](docs/architecture.md).

## Run

```bash
docker compose up --build
```

## Key endpoints

- Gateway: `http://localhost:8080`
- Customer Service: `http://localhost:8081`
- Loan Service: `http://localhost:8082`
- Risk Service: `http://localhost:8083`
- Notification Service: `http://localhost:8084`

## Example request

```bash
curl -X POST http://localhost:8080/api/loans/apply   -H "Content-Type: application/json"   -d '{
    "customerId":"CUST-1001",
    "amount":25000,
    "termMonths":36,
    "purpose":"HOME_IMPROVEMENT"
  }'
```

## GitHub push

I cannot directly connect to your GitHub account from here, but this repo is ready to publish:

```bash
git init
git add .
git commit -m "Initial commit: reactive banking platform"
git branch -M main
git remote add origin https://github.com/<your-username>/banking-reactive-platform.git
git push -u origin main
```
