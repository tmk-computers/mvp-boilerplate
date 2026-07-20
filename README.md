# DASP Digital MVP Boilerplate Framework (`dasp-mvp-boilerplate`)

> **Enterprise-Grade Reusable Application Framework for DASP Digital**  
> Accelerate production-ready business application development (CRM, ERP, Billing, POS, HRMS, Logistics, Healthcare, School ERP, Warehousing, etc.) by cutting development effort by over **80%**.

---

## 🚀 Executive Summary

`dasp-mvp-boilerplate` is an enterprise application framework pre-configured with multi-tenant dynamic RBAC, token management, generic CRUD base layers, unified logging, pluggable storage, strategy-pattern notifications, offline-first mobile sync, dynamic UI form engines, and a complete Dockerized microservice-ready environment.

Instead of writing boilerplate security, JPA mapping, table pagination, authentication, file storage, or audit logging from scratch for every project, developers implement **only business modules**.

---

## 🎯 Target Application Domains

The framework is architected to rapidly construct applications including but not limited to:

| Category | Typical Applications |
| :--- | :--- |
| **Enterprise & Core** | CRM, ERP, HRMS, Payroll, Accounting, Billing, Invoicing |
| **Operations & Trade** | Inventory, Warehouse, Retail POS, Manufacturing, Asset Management |
| **Services & Care** | Hospital, Clinic, School ERP, College ERP, Sports Academy |
| **Customer & Portal** | Customer Portals, Ticket/Complaint Management, Service Management |
| **Subscriptions & NGO**| NGO Management, Society/Community, Subscription & Membership |

---

## 🛠️ Technology Stack Matrix

### Backend Architecture
- **Language**: Java 21 LTS
- **Framework**: Spring Boot 3.4.x
- **Security**: Spring Security 6.x + JWT + Redis Token Blacklist + Rate Limiting
- **Persistence**: Spring Data JPA + Hibernate 6.x + PostgreSQL 16
- **Database Migrations**: Flyway 10+
- **Cache & In-Memory Storage**: Redis 7.x
- **DTO Mapping**: MapStruct 1.6+
- **Boilerplate Reduction**: Lombok
- **API Documentation**: OpenAPI 3.0 / Swagger UI (Springdoc)
- **Containerization**: Docker & Docker Compose

### Web Frontend Architecture
- **Core Framework**: React 18/19 + TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS v3/v4 + Headless UI / Lucide Icons
- **Server State & Caching**: TanStack Query v5 (React Query)
- **Routing**: React Router v6
- **Form Management**: React Hook Form + Zod Schema Validation
- **HTTP Client**: Axios with automatic JWT silent refresh interceptors

### Mobile Application Architecture
- **Framework**: React Native + TypeScript
- **Navigation**: React Navigation v6
- **Local Storage & Offline Sync**: SQLite + MMKV + Background Sync Queue
- **Security & Biometrics**: React Native Keychain + Biometric Auth
- **Hardware Integration**: Camera, QR/Barcode Scanner

---

## 🏛️ Project Directory Structure

```text
dasp-mvp-boilerplate/
├── backend/                  # Java 21 / Spring Boot 3 Backend Microservice
│   ├── src/main/java/com/dasp/framework/
│   │   ├── core/            # Generic Base Classes (Entity, Controller, Service, Repo, Response)
│   │   ├── security/        # JWT, Dynamic RBAC, Redis Blacklist, Security Headers
│   │   ├── modules/         # Reusable Modules (Auth, User, Role, Org, File, Audit, Settings)
│   │   ├── notification/    # Strategy Pattern (Email, SMS, WhatsApp, Push, In-App)
│   │   ├── storage/         # Storage Abstraction (Local, MinIO, AWS S3)
│   │   └── logging/         # Structured JSON Logging + MDC Correlation IDs
│   └── src/main/resources/
│       └── db/migration/    # Flyway Versioned Migrations & Seed Scripts
├── frontend/                 # React 18 + Vite + TS Enterprise Dashboard
│   └── src/
│       ├── components/      # UI Component Library (Buttons, Tables, Forms, Modals, Dynamic Forms)
│       ├── context/         # Auth & Theme State Providers
│       ├── hooks/           # Custom React Hooks (useAuth, usePermissions, useApiQuery)
│       ├── services/        # Axios Client & Interceptors
│       └── pages/           # Admin Pages (Users, Roles, Organizations, Audit Logs, Dashboard)
├── mobile/                   # React Native + TypeScript Cross-Platform App
│   └── src/
│       ├── navigation/      # Stack & Tab Navigators
│       ├── storage/         # SQLite Offline Database & Queue Manager
│       ├── screens/         # Mobile Screens (Login, Dashboard, Offline Status)
│       └── components/      # Native Custom Components
├── docs/                     # 13 Enterprise Technical Specification Documents
├── docker/                   # Docker Compose environment (Postgres, Redis, PgAdmin, Redis Insight)
├── scripts/                  # Developer environment setup & seed scripts
└── .github/workflows/        # Automated CI/CD Pipelines
```

---

## ⚡ Quickstart Guide

### Prerequisites
- **Java**: OpenJDK 21 or GraalVM 21
- **Node.js**: v20 LTS or higher
- **Docker & Docker Compose**: v24+
- **Maven**: 3.9+ (or use `./mvnw`)

### 1. Clone & Setup Environment
```bash
git clone https://github.com/dasp-digital/dasp-mvp-boilerplate.git
cd dasp-mvp-boilerplate
```

### 2. Launch Local Infrastructure (PostgreSQL & Redis)
```bash
docker-compose -f docker/docker-compose.yml up -d
```
*Access infrastructure tools:*
- **PgAdmin 4**: http://localhost:5050 (`admin@dasp.com` / `admin123`)
- **Redis Insight**: http://localhost:5540

### 3. Start Backend Application
```bash
cd backend
./mvnw spring-boot:run
```
*Backend API Services:*
- **Swagger API Docs**: http://localhost:8080/swagger-ui.html
- **Actuator Health**: http://localhost:8080/actuator/health

### 4. Start Web Frontend
```bash
cd ../frontend
npm install
npm run dev
```
*Access Web Dashboard at http://localhost:5173* (Default Admin Credentials: `admin@dasp.com` / `Admin@123`)

---

## 📚 Comprehensive Documentation Index

Explore the full architectural specifications in the `docs/` folder:

1. 🏛️ [ARCHITECTURE.md](docs/ARCHITECTURE.md) - System architecture, sequence diagrams, SOLID principles.
2. ☕ [BACKEND.md](docs/BACKEND.md) - Spring Boot layer architecture, base classes, caching & logging.
3. ⚛️ [FRONTEND.md](docs/FRONTEND.md) - React design system, permissions, Axios interceptors & state.
4. 📱 [MOBILE.md](docs/MOBILE.md) - React Native offline queue, SQLite sync, biometrics & camera.
5. 🗄️ [DATABASE.md](docs/DATABASE.md) - PostgreSQL standards, UUID primary keys, soft delete & Flyway.
6. 🔒 [SECURITY.md](docs/SECURITY.md) - Spring Security, JWT rotation, Redis blacklist, dynamic RBAC.
7. 🐳 [DEPLOYMENT.md](docs/DEPLOYMENT.md) - Docker Compose, Nginx, SSL certificates, health monitoring.
8. 🌐 [API-STANDARDS.md](docs/API-STANDARDS.md) - REST guidelines, `ApiResponse<T>`, pagination & versioning.
9. 🤝 [CONTRIBUTING.md](docs/CONTRIBUTING.md) - Git Flow, Conventional Commits & Pull Request rules.
10. 🧪 [TESTING.md](docs/TESTING.md) - JUnit 5, Mockito, Integration & Web/Mobile UI tests.
11. 📝 [CHANGELOG.md](docs/CHANGELOG.md) - Version history & semantic release tracking.
12. 📏 [CODING-STANDARDS.md](docs/CODING-STANDARDS.md) - Code style, formatting, Checkstyle & ESLint rules.

---

## 📄 License & Proprietary Notice
Copyright © 2026 **DASP Digital**. All rights reserved. Proprietary reusable framework for internal and commercial application developments.
