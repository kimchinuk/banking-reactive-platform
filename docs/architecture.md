# Architecture (HLD / LLD)

## High-Level Design (HLD)

```mermaid
flowchart LR
    A[Client / Banking Channel] --> G[API Gateway
Spring Cloud Gateway]
    G --> L[Loan Service
WebFlux + WebClient]
    L -->|HTTP| C[Customer Service
WebFlux]
    L -->|Publish loan-events| K[(Kafka)]
    K --> R[Risk Service
Kafka Consumer/Producer]
    R -->|Publish loan-decisions| K
    K --> N[Notification Service
Kafka Consumer]
```

### HLD explanation

- **API Gateway** centralizes routing and is the future home for authentication, rate limiting, and correlation IDs.
- **Loan Service** owns synchronous input handling and business orchestration.
- **Customer Service** is a backing reference-data service.
- **Kafka** decouples the request path from downstream risk and notification processing.
- **Risk Service** can scale independently and evolve without changing the entry API.
- **Notification Service** represents downstream side effects such as email/SMS/push.

## Low-Level Design (LLD)

```mermaid
sequenceDiagram
    autonumber
    participant Client
    participant Gateway
    participant Loan
    participant Customer
    participant Kafka
    participant Risk
    participant Notification

    Client->>Gateway: POST /api/loans/apply
    Gateway->>Loan: Forward request
    Loan->>Customer: GET /customers/{id}
    alt Customer available
        Customer-->>Loan: CustomerResponse
        Loan->>Loan: Validate business rules
        Loan->>Kafka: Publish LoanEvent
        Loan-->>Gateway: 202 Accepted
        Kafka-->>Risk: Consume LoanEvent
        Risk->>Risk: Score/decide
        Risk->>Kafka: Publish LoanDecisionEvent
        Kafka-->>Notification: Consume LoanDecisionEvent
    else Customer unavailable
        Loan->>Loan: Circuit breaker fallback
        Loan-->>Gateway: 503 / fallback
    end
```

## Spring technology notes

### Spring WebFlux
Used for non-blocking request handling and reactive composition with `Mono`. This reduces the cost of waiting on I/O during heavy concurrency.

### Spring WebClient
Used by the Loan Service so downstream HTTP calls do not block request threads.

### Spring Cloud Gateway
Provides a reactive API gateway at the platform edge.

### Spring Kafka
Implements event-driven communication between services.

### Resilience4j Circuit Breaker
Protects the Loan Service from latency amplification and repeated failures when Customer Service is unavailable.
