# DTB Senior Java Engineer Technical Test

This project is a simplified microservices-based banking platform implemented using **Spring Boot WebFlux**, **Kafka**, and **PostgreSQL**, fully containerized with **Docker Compose**.

Each microservice runs independently and communicates via REST (WebClient) and Kafka.

---

## 🧱 Services Overview

| Service               | Description                                       | Port  | Context Path         |
|-----------------------|---------------------------------------------------|-------|-----------------------|
| `profile-svc`         | Manages customer profiles, login, registration   | 8081  | `/api/profile`        |
| `store-of-value-svc`  | Manages accounts: balance, activation, updates   | 8082  | `/api/store`          |
| `payment-svc`         | Handles topups, withdrawals, and transfers       | 8083  | `/api/payment`        |
| `events-svc`          | Listens for transaction events, sends notifications | 8084  | `/api/events`       |

---

## 🛠️ Stack

- Java 21
- Spring Boot 3.x (WebFlux)
- R2DBC + PostgreSQL
- Kafka + Zookeeper
- Gradle (Kotlin DSL)
- JUnit 5 + Mockito
- Docker + Docker Compose
- springdoc-openapi-webflux for Swagger

---

## 🚀 Getting Started

### Prerequisites

- [Docker](https://www.docker.com/)
- [Java 21 SDK](https://adoptium.net/)
- [Gradle 8+ (optional, included in wrapper)](https://gradle.org/)

---

### 🔄 Run All Services (Docker)

```bash
docker-compose up --build
```

All services will be built and started with:

- PostgreSQL (one per service)
- Kafka & Zookeeper
- Auto-configured ports and Swagger docs

---

## 📦 API Documentation

Once services are running, Swagger UI will be available at:

- [`http://localhost:8081/swagger-ui.html`](http://localhost:8081/swagger-ui.html) – Profile
- [`http://localhost:8082/swagger-ui.html`](http://localhost:8082/swagger-ui.html) – Store of Value
- [`http://localhost:8083/swagger-ui.html`](http://localhost:8083/swagger-ui.html) – Payment
- [`http://localhost:8084/swagger-ui.html`](http://localhost:8084/swagger-ui.html) – Events

---

## 📂 Project Structure

```plaintext
dtb-snr-java-interview/
├── profile-svc/
├── store-of-value-svc/
├── payment-svc/
├── events-svc/
├── docker-compose.yml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🧪 Testing

Run tests with:

```bash
./gradlew test
```

Includes:
- Unit tests with JUnit 5
- Mocking with Mockito
- Integration tests per service

---

## 📌 Notes

- Each service uses its own isolated PostgreSQL DB (microservice boundaries respected)
- JWT is used for authentication, with RBAC
- Kafka used for transactional event delivery

---

## 👨‍💻 Author

**Samuel Mugi Macharia**  
_Senior Java Engineer - Technical Test Submission_
