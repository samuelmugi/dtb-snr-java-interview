# 🧱 System Design Overview

This document describes the architecture and design decisions for the DTB Banking Microservices Platform, developed using Spring WebFlux, Kafka, and PostgreSQL.

---

## 🗺️ High-Level Architecture Diagram

```
                        ┌──────────────────────┐
                        │   API Gateway / LB   │
                        └──────────┬───────────┘
                                   │
               ┌──────────────────┼───────────────────┐
               │                  │                   │
     ┌─────────▼──────┐  ┌────────▼────────┐   ┌──────▼────────┐
     │ profile-svc    │  │ payment-svc     │   │ store-of-value│
     │  + PostgreSQL  │  │  + PostgreSQL   │   │  + PostgreSQL │
     └────────────────┘  └────────┬────────┘   └──────┬─────────┘
                                   │                   │
                             ┌────▼────┐        ┌──────▼──────┐
                             │ Kafka MQ│────────► events-svc  │
                             └─────────┘        │  (no DB)    │
                                                └─────────────┘
```

---

## 🧩 Architecture Rationale

### Microservices with Independent Databases

- Each service owns its own **PostgreSQL** database.
- No shared schemas or cross-service joins.
- Services are deployed, scaled, and backed up independently.

### Spring WebFlux

- Fully non-blocking reactive architecture.
- Uses `WebClient` for async inter-service communication.

### Security

- JWT-based authentication issued by `profile-svc`.
- Role-based access control (RBAC).
- All services validate JWT locally.
- Communication is secured using HTTPS in production.

---

## 🔁 Data Consistency

| Scope         | Strategy                                |
|---------------|------------------------------------------|
| Intra-service | R2DBC + `@Transactional` + `@Version`   |
| Inter-service | Kafka + Eventual Consistency            |
| Boundaries    | No direct DB access between services     |
| Shared data   | Fetched via REST or consumed via Kafka  |

---

## 📬 Inter-Service Communication

| Type       | Method          | Used For                        |
|------------|------------------|----------------------------------|
| REST       | `WebClient`     | Profile validation, Account lookup |
| Messaging  | Kafka           | Event publishing (e.g. transactions) |
| Auth       | JWT             | Authenticated API access        |

---

## ⚙️ Scalability, Availability, and Disaster Recovery

| Component         | Scalability       | HA Strategy                    |
|------------------|--------------------|--------------------------------|
| profile-svc       | Horizontally scalable | Independent container, DB backup |
| store-of-value-svc| Horizontally scalable | Same                          |
| payment-svc       | Horizontally scalable | Same                          |
| events-svc        | Kafka consumer scaling | Stateless, easy recovery     |
| PostgreSQL DBs    | Per-service DBs with WAL backups | Volume mounts, cloud managed DB optional |

---

## ✅ Key Advantages

- Microservice autonomy with clear boundaries.
- High scalability and observability.
- Secure, fault-isolated, and reactive-ready.
- Ready for cloud-native deployment via Docker/K8s.
