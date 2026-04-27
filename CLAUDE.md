# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

E-commerce platform built with a microservices architecture. Services communicate via REST APIs. The primary implementation is the **Quarkus API REST** service (`Quarkus/api-rest/`). All other services are currently in the design/documentation phase.

## Build and Run Commands

All commands run from `Quarkus/api-rest/`.

```bash
# Development mode with live reload (Dev UI at http://localhost:8080/q/dev/)
./mvnw quarkus:dev

# Run tests
./mvnw test

# Package as JVM JAR
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar

# Build native executable (requires GraalVM, or use container build)
./mvnw package -Dnative -Dquarkus.native.container-build=true

# Docker (JVM)
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/api-rest-jvm .
docker run -i --rm -p 8080:8080 quarkus/api-rest-jvm
```

Integration tests are skipped by default (`skipITs=true` in pom.xml).

## Architecture

### Technology Stack
- **Quarkus** 3.33.1 with **Java 21**
- **Jakarta REST** (`quarkus-rest`) + **Jackson** (`quarkus-rest-jackson`) for JSON
- **Hibernate Validator** for bean validation
- **Quarkus ARC** for CDI dependency injection
- **JUnit 5** + **REST-Assured** for testing

### OpenAPI-First Design

The API contract is defined in:
```
Quarkus/api-rest/src/main/resources/api/oas-e-commerce-v1.yaml
```

Model classes are **auto-generated** from this spec at build time using `openapi-generator-maven-plugin` (v7.1.0). Generated sources land in:
```
target/generated-sources/openapi/src/main/java/com/ecommerce/api/model/
```

Configuration: `generateApis=false`, `generateModels=true` — only models are generated, not API stubs. Resource/controller classes must be written manually in `src/main/java/com/ecommerce/api/`.

**Do not edit generated model classes directly.** Modify `oas-e-commerce-v1.yaml` and rebuild.

### Microservices (Planned)

The system is designed around these independent services, all under base path `/api/v1`:

| Service | Responsibility | Key Endpoints |
|---|---|---|
| User Service | Registration, login, user management | `/users`, `/users/{id}`, `/users/login` |
| Product Service | Product catalog, inventory | `/products`, `/products/{id}` |
| Order Service | Order lifecycle | `/orders`, `/orders/{id}` |
| Order Items Service | Per-order line items | `/orders/{id}/items`, `/orders/{id}/items/{itemId}` |
| Payment Service | Payment processing | `/orders/{id}/payment` |

### Business Logic States

**Order states:** `PENDIENTE` → `CONFIRMADO` → `ENVIADO` → `ENTREGADO` (or `CANCELADO`)

**Payment states:** `PENDIENTE` → `PAGADO` (or `FALLIDO`, `REEMBOLSADO`)

### Roles

- **Admin**: full access to all users, products, orders, and payments
- **User**: can manage own profile, browse products, create/view own orders and payments

### Service Flow

User registers/logs in → browses products → creates order → adds order items → processes payment

## Current Implementation Status

- **Implemented:** Quarkus framework wired up, OpenAPI model generation, `GreetingResource` sample endpoint at `GET /hello`
- **Not yet implemented:** Actual resource endpoints, database persistence, auth middleware, service-to-service communication, Docker Compose, CI/CD
- **`services/auth-service/`**: README only, no code

## Key Configuration

- `Quarkus/api-rest/src/main/resources/application.properties` — currently empty; add Quarkus config here
- API runs on port **8080** by default
- Base Docker images: `ubi9/openjdk-21-runtime:1.24` (JVM) and `ubi9/ubi-minimal:9.7` (native)
