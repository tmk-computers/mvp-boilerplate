# Framework Changelog (`CHANGELOG.md`)

> **DASP Digital MVP Boilerplate Framework**  
> All notable changes to this project will be documented in this file using Semantic Versioning (`MAJOR.MINOR.PATCH`).

---

## [1.0.0] - 2026-07-20

### 🚀 Initial Release
- **Backend Infrastructure**:
  - Java 21 LTS & Spring Boot 3.4.x foundation.
  - Reusable `BaseEntity`, `BaseAuditEntity`, `BaseController`, `BaseService`, `BaseRepository`, `BaseMapper`, and `BaseResponse`.
  - Dynamic Role-Based Access Control (RBAC) with fine-grained database permissions cached in Redis.
  - JWT Authentication, Refresh Tokens, and instant Redis token blacklist on logout.
  - Notification strategy engine supporting Email (SMTP), SMS, WhatsApp, Push (FCM), and In-App.
  - File storage strategy supporting Local Disk, MinIO, and AWS S3.
  - Flyway migrations (`V1__initial_schema.sql` and `V2__seed_rbac_data.sql`).
  - Structured JSON logging with trace/correlation ID MDC context.
- **Web Frontend**:
  - React 18/19, Vite, TypeScript, Tailwind CSS, TanStack Query, React Router v6, React Hook Form + Zod.
  - Dynamic UI component library (Button, Table, Form, DatePicker, Modal, FileUpload, DynamicForm).
  - Silent token refresh Axios interceptors and `<PermissionGate />` authorization component.
- **Mobile Application**:
  - React Native TypeScript foundation with SQLite offline database and Sync Queue Manager.
  - Secure storage, biometric login, and hardware scanner drivers.
- **DevOps & Infrastructure**:
  - Docker Compose setup for PostgreSQL 16, Redis 7, PgAdmin 4, and Redis Insight.
  - GitHub Actions CI/CD workflows.
