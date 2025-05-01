# 📄 Service Documentation

This document outlines the API specifications and setup instructions for all microservices in the DTB Banking Platform.

---

## 🧰 Technologies Used

- Java 21
- Spring Boot 3.x with WebFlux
- PostgreSQL with R2DBC
- Kafka & Zookeeper
- Docker & Docker Compose
- springdoc-openapi-webflux for Swagger

---

## 🚀 Running the Services Locally

### Prerequisites

- [Docker](https://www.docker.com/)
- [Java 21 SDK](https://adoptium.net/)
- Gradle (or use the included `./gradlew` wrapper)

### Steps

```bash
# Clone the project
git clone https://github.com/your-username/dtb-snr-java-interview.git
cd dtb-snr-java-interview

# Build all services
./gradlew clean build

# Run all services, PostgreSQL databases, and Kafka
docker-compose up --build
```

---

## 📦 API Documentation (Swagger UI)

Swagger UI is automatically available per service:

| Service               | Swagger URL                                  |
|------------------------|----------------------------------------------|
| `profile-svc`          | http://localhost:8081/swagger-ui.html        |
| `store-of-value-svc`   | http://localhost:8082/swagger-ui.html        |
| `payment-svc`          | http://localhost:8083/swagger-ui.html        |
| `events-svc`           | http://localhost:8084/swagger-ui.html        |

---

## 🔐 Authentication & Authorization

- Login via: `POST /auth/login` on `profile-svc`
- Response includes a JWT token
- Use this token for all authenticated endpoints:

```http
Authorization: Bearer <jwt-token>
```

Roles are embedded in the JWT token (e.g., `USER`, `ADMIN`).

---

## 🧪 Running Tests

```bash
./gradlew test
```

Tests include:
- Unit tests with JUnit 5 + Mockito
- Integration tests using `@SpringBootTest` and `WebTestClient`

---

## 📬 Kafka Topics

Kafka is used for asynchronous communication between services.

| Topic               | Publisher      | Consumer        | Purpose                           |
|---------------------|----------------|------------------|------------------------------------|
| `transaction-events`| `payment-svc`  | `events-svc`     | Notify users of completed transactions |

---

## 🧭 Service Context Paths

Each service is accessible at:

| Service               | Context Path       |
|------------------------|--------------------|
| `profile-svc`          | `/api/profile`     |
| `store-of-value-svc`   | `/api/store`       |
| `payment-svc`          | `/api/payment`     |
| `events-svc`           | `/api/events`      |

---

## 📁 Directory Structure

```plaintext
dtb-snr-java-interview/
├── profile-svc/
├── store-of-value-svc/
├── payment-svc/
├── events-svc/
├── docker-compose.yml
├── README.md
├── SERVICE_DOCUMENTATION.md
├── SYSTEM_DESIGN.md
└── build.gradle.kts
```

For architecture details, see [`SYSTEM_DESIGN.md`](./SYSTEM_DESIGN.md).
