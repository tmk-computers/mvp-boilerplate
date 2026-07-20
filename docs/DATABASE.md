# Database Standards Specification (`DATABASE.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Database Engine: PostgreSQL 16 | Migration: Flyway 10+ | Caching: Redis 7

---

## 🗄️ 1. Naming & Type Conventions

| Rule Element | Convention Standard | Example |
| :--- | :--- | :--- |
| **Table Names** | Lowercase, plural, snake_case | `users`, `role_permissions`, `audit_logs` |
| **Column Names** | Lowercase, snake_case | `first_name`, `created_date`, `is_deleted` |
| **Primary Keys** | `id` of type `UUID` (v4) | `id UUID PRIMARY KEY DEFAULT gen_random_uuid()` |
| **Foreign Keys** | Singular entity name + `_id` | `user_id`, `role_id`, `organization_id` |
| **Indexes** | `idx_<tablename>_<columnname>` | `idx_users_email`, `idx_audit_logs_trace_id` |

---

## 🛡️ 2. Auditing & Soft Delete Standard Columns

Every business table extending `BaseAuditEntity` MUST include the following 7 standard columns:

```sql
id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
version       BIGINT NOT NULL DEFAULT 0,
created_by    VARCHAR(100),
created_date  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_by    VARCHAR(100),
updated_date  TIMESTAMP WITH TIME ZONE,
deleted_by    VARCHAR(100),
deleted_date  TIMESTAMP WITH TIME ZONE,
is_deleted    BOOLEAN NOT NULL DEFAULT FALSE
```

---

## 🚀 3. Indexing Strategy

1. **Soft Delete Composite Indexes**: Always index `is_deleted` together with frequently queried columns:
   ```sql
   CREATE INDEX idx_users_email_deleted ON users(email, is_deleted);
   ```
2. **Audit Timestamp Indexes**: Index `created_date` for chronological reporting and audit retrieval:
   ```sql
   CREATE INDEX idx_audit_logs_created_date ON audit_logs(created_date DESC);
   ```

---

## 📜 4. Database Migration Guidelines (Flyway)

- Migrations reside in `backend/src/main/resources/db/migration/`.
- Naming format: `V<version>__<description>.sql` (e.g. `V1__initial_schema.sql`, `V2__seed_rbac_data.sql`).
- **Never modify an already executed Flyway migration script**. Create a new versioned script for changes.
