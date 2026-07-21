# DASP Digital MVP Boilerplate Framework (`dasp-mvp-boilerplate`)

> **Enterprise-Grade Reusable Application Framework & AI Platform for DASP Digital**  
> Accelerate production-ready business application development (CRM, ERP, Billing, POS, HRMS, Logistics, Healthcare, School ERP, Warehousing, etc.) with built-in Enterprise AI capability, cutting development effort by over **80%**.

---

## 🚀 Executive Summary

`dasp-mvp-boilerplate` is an enterprise application framework pre-configured with multi-tenant dynamic RBAC, token management, generic CRUD base layers, unified logging, pluggable storage, strategy-pattern notifications, offline-first mobile sync, dynamic UI form engines, a complete Dockerized microservice-ready environment, and an **Enterprise AI Framework Layer**.

Instead of writing boilerplate security, JPA mapping, table pagination, authentication, file storage, audit logging, or LLM integrations from scratch for every project, developers implement **only business modules**.

---

## 🤖 Enterprise AI Framework Layer

The framework includes a vendor-agnostic **Enterprise AI Platform** (`com.dasp.framework.modules.ai`) that acts as an abstraction between business applications and external AI service providers:

- **Zero Provider Coupling**: Business modules interact strictly via `AIProvider` and `VectorProvider` interfaces. Providers are dynamically configured without modifying application logic.
- **Multi-LLM Provider Support**: Out-of-the-box support for OpenAI (`gpt-4o`), Google Gemini (`gemini-1.5-pro`), Anthropic Claude (`claude-3-5-sonnet`), Azure OpenAI, and Local Ollama (`llama3.2` zero-cost execution).
- **Retrieval Augmented Generation (RAG)**: Integrated document chunking, embedding generation, and vector database abstraction supporting PgVector, Qdrant, Milvus, Pinecone, and Chroma.
- **Database-Driven Prompt Engine**: Version-controlled prompts with runtime variable interpolation (`{{customerName}}`), validation, rollback, and Redis prompt caching.
- **Autonomous AI Agent & Tool Framework**: Goal-oriented agent execution engine with reusable business tools (`DatabaseSearchTool`, `RestApiTool`, `EmailTool`, `CalculatorTool`).
- **AI Security & Governance**: Built-in prompt injection defense, PII data masking (emails, phone numbers), AI content moderation, token rate limits, and real-time Redis cost tracking.

---

## 🎯 Target Application Domains

The framework is architected to rapidly construct applications including but not limited to:

| Category | Typical Applications | AI-Enabled Features |
| :--- | :--- | :--- |
| **Enterprise & Core** | CRM, ERP, HRMS, Payroll, Accounting, Billing | Automated lead scoring, document extraction, invoice OCR, report summaries |
| **Operations & Trade** | Inventory, Warehouse, Retail POS, Manufacturing | Stock reorder forecasting, automated PO generation, receipt scanning |
| **Services & Care** | Hospital, Clinic, School ERP, College ERP | Patient record summaries, student performance analytics, voice transcription |
| **Customer & Portals**| Customer Portals, Ticket/Complaint Management | AI Support Copilot, automated ticket classification, intelligent response drafting |
| **Subscriptions & NGO**| NGO Management, Society/Community, Subscription | Member engagement insights, automated email/WhatsApp follow-ups |

---

## 🛠️ Technology Stack Matrix

### Backend Architecture
- **Language**: Java 21 LTS
- **Framework**: Spring Boot 3.4.x
- **Security**: Spring Security 6.x + JWT + Redis Token Blacklist + Rate Limiting
- **AI Engine**: Vendor-Agnostic AIProvider Abstraction (OpenAI, Gemini, Claude, Azure, Ollama)
- **Vector Search & RAG**: PgVector, Qdrant, Milvus, Pinecone, Chroma
- **Persistence**: Spring Data JPA + Hibernate 6.x + PostgreSQL 16
- **Database Migrations**: Flyway 10+ (Migrations V1, V2, V3)
- **Cache & In-Memory Storage**: Redis 7.x (Token blacklist, dynamic RBAC, prompt & cost caching)
- **DTO Mapping**: MapStruct 1.6+
- **Boilerplate Reduction**: Lombok
- **API Documentation**: OpenAPI 3.0 / Swagger UI (Springdoc)
- **Containerization**: Docker & Docker Compose

### Web Frontend Architecture
- **Core Framework**: React 18/19 + TypeScript
- **Build Tool**: Vite
- **AI Components**: Floating `AIChatWidget` assistant drawer & `AdminAIPromptsPage` UI
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
- **Hardware & AI**: Camera, QR/Barcode Scanner, Speech-to-Text Voice Assistant

---

## 🏛️ High-Level Layered Architecture

```text
 Presentation Layer (Web Dashboard & Mobile App)
                        │
                        ▼
 Business Application Layer (CRM, ERP, Billing, HRMS, Hospital, etc.)
                        │
                        ▼
 ┌─────────────────────────────────────────────────────────┐
 │               AI Framework Layer (`modules/ai`)         │
 │ (AIProvider, PromptEngine, RAGPipeline, AgentEngine)    │
 └──────────────────────────┬──────────────────────────────┘
                            │
                            ▼
 ┌─────────────────────────────────────────────────────────┐
 │            Provider Abstraction & Factory Layer         │
 └───────┬──────────────┬──────────────┬──────────────┬────┘
         │              │              │              │
         ▼              ▼              ▼              ▼
      OpenAI          Gemini         Claude         Ollama
   (Cloud LLM)    (Multimodal)    (Sonnet 3.5)   (Local/Airgapped)
```

---

## 📁 Project Directory Structure

```text
dasp-mvp-boilerplate/
├── backend/                  # Java 21 / Spring Boot 3 Backend Microservice
│   ├── src/main/java/com/dasp/framework/
│   │   ├── core/            # Generic Base Classes (Entity, Controller, Service, Repo, Response)
│   │   ├── security/        # JWT, Dynamic RBAC, Redis Blacklist, Security Headers
│   │   ├── modules/
│   │   │   ├── ai/          # Enterprise AI Platform (Providers, RAG, Prompts, Agents, Tools, Security, Cost)
│   │   │   ├── auth/        # Login, Logout, Refresh, Password Management
│   │   │   ├── user/        # User CRUD & State Management
│   │   │   ├── role/        # Role & Fine-Grained Permission Assignment
│   │   │   ├── org/         # Organization, Branch, Department Hierarchy
│   │   │   ├── audit/       # Automated Entity & API Audit Logs
│   │   │   └── setting/     # Key-Value Master Data Caching
│   │   ├── notification/    # Strategy Pattern (Email, SMS, WhatsApp, Push, In-App)
│   │   ├── storage/         # Storage Abstraction (Local, MinIO, AWS S3)
│   │   └── logging/         # Structured JSON Logging + MDC Correlation IDs
│   └── src/main/resources/
│       └── db/migration/    # Flyway Versioned Migrations (V1, V2, V3 Schema)
├── frontend/                 # React 18 + Vite + TS Enterprise Dashboard
│   └── src/
│       ├── components/      # UI Component Library (Buttons, Tables, Forms, AIChatWidget)
│       ├── context/         # Auth & Theme State Providers
│       ├── services/        # Axios Client & Interceptors
│       └── pages/           # Admin Pages (Users, Roles, AI Prompts, Dashboard)
├── mobile/                   # React Native + TypeScript Cross-Platform App
│   └── src/
│       ├── navigation/      # Stack & Tab Navigators
│       ├── storage/         # SQLite Offline Database & Sync Queue Manager
│       └── screens/         # Mobile Screens (Login, Dashboard, Offline Status)
├── docs/                     # 18 Enterprise Technical Specification Documents
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
cp .env.example .env
```

### 2. Configure AI Provider Keys (Optional)
Edit `.env` to configure your preferred AI provider:
```env
OPENAI_API_KEY=sk-proj-xxxxxx
GEMINI_API_KEY=AIzaSyxxxxxx
OLLAMA_BASE_URL=http://localhost:11434
```

### 3. Launch Infrastructure (PostgreSQL & Redis)
```bash
docker-compose -f docker/docker-compose.yml up -d
```

### 4. Start Backend Application
```bash
cd backend
./mvnw spring-boot:run
```
*Backend API Services:*
- **Swagger API Docs**: http://localhost:8080/swagger-ui.html
- **AI Chat Endpoint**: `POST http://localhost:8080/api/v1/ai/chat`
- **Actuator Health**: http://localhost:8080/actuator/health

### 5. Start Web Frontend
```bash
cd ../frontend
npm install
npm run dev
```
*Access Web Dashboard & Floating AI Assistant at http://localhost:5173*  
(Default Admin Credentials: `admin@dasp.com` / `Admin@123`)

---

## 📚 Comprehensive Documentation Index

Explore the full technical specifications in the `docs/` directory:

1. 🏛️ [ARCHITECTURE.md](docs/ARCHITECTURE.md) - System architecture, sequence diagrams, SOLID principles.
2. ☕ [BACKEND.md](docs/BACKEND.md) - Spring Boot layer architecture, base classes, caching & logging.
3. ⚛️ [FRONTEND.md](docs/FRONTEND.md) - React design system, permissions, Axios interceptors & state.
4. 📱 [MOBILE.md](docs/MOBILE.md) - React Native offline queue, SQLite sync, biometrics & camera.
5. 🤖 [AI.md](docs/AI.md) - Enterprise AI Platform, provider abstraction (OpenAI, Gemini, Claude, Ollama).
6. 🔍 [RAG.md](docs/RAG.md) - Retrieval Augmented Generation, chunking, vector DB abstraction (PgVector, Qdrant).
7. 🤖 [AGENTS.md](docs/AGENTS.md) - Goal-oriented autonomous AI agents and tool framework.
8. 📝 [PROMPTS.md](docs/PROMPTS.md) - DB-driven prompt versioning and variable template engine.
9. 🛡️ [SECURITY-AI.md](docs/SECURITY-AI.md) - Prompt injection protection, PII masking & AI cost audit.
10. 📡 [API-AI.md](docs/API-AI.md) - REST API specification for `/api/v1/ai/*` endpoints.
11. 🗄️ [DATABASE.md](docs/DATABASE.md) - PostgreSQL standards, UUID primary keys, soft delete & Flyway.
12. 🔒 [SECURITY.md](docs/SECURITY.md) - Spring Security, JWT rotation, Redis blacklist, dynamic RBAC.
13. 🐳 [DEPLOYMENT.md](docs/DEPLOYMENT.md) - Docker Compose, Nginx, SSL certificates, health monitoring.
14. 💻 [LOCAL-SETUP.md](docs/LOCAL-SETUP.md) - Step-by-step local setup, IDE execution, Ollama AI, troubleshooting.
15. 🌐 [API-STANDARDS.md](docs/API-STANDARDS.md) - REST guidelines, `ApiResponse<T>`, pagination & versioning.
16. 🤝 [CONTRIBUTING.md](docs/CONTRIBUTING.md) - Git Flow, Conventional Commits & Pull Request rules.
17. 🧪 [TESTING.md](docs/TESTING.md) - JUnit 5, Mockito, Integration & Web/Mobile UI tests.
18. 📝 [CHANGELOG.md](docs/CHANGELOG.md) - Version history & semantic release tracking.
19. 📏 [CODING-STANDARDS.md](docs/CODING-STANDARDS.md) - Code style, formatting, Checkstyle & ESLint rules.

---

## 📄 License & Proprietary Notice
Copyright © 2026 **DASP Digital**. All rights reserved. Proprietary reusable framework & AI platform for internal and commercial application developments.
