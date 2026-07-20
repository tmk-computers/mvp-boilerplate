# Backend Technical Specification (`BACKEND.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Technology: Java 21 LTS | Spring Boot 3.4.x | Spring Security 6.x | Spring Data JPA | PostgreSQL 16 | Redis 7

---

## 🏗️ 1. Architecture Overview & Package Layout

The backend enforces a strict layered architecture:

```text
HTTP Request
     │
     ▼
[ Controller Layer ]      <---> Request / Response DTOs
     │
     ▼
[ MapStruct Mapper ]      <---> Converts DTO <-> Entity
     │
     ▼
[ Service Interface ]     <---> Business Rules & Contract
     │
     ▼
[ Service Impl ]          <---> Transaction Management & Events
     │
     ▼
[ Repository Layer ]      <---> Spring Data JPA / Soft Delete Queries
     │
     ▼
[ PostgreSQL / Redis ]
```

### Mandatory Rules
1. Controllers must **NEVER** interact directly with Repositories.
2. Entities must **NEVER** be returned directly by Controllers (Always use DTOs).
3. All service methods are contract-first: interface defined in `com.dasp.framework.modules.<module>.service`, implementation in `impl` subpackage.
4. Business logic must be stateless and idempotent where possible.

### Package Architecture

```text
com.dasp.framework/
├── core/                        # Foundation Framework Infrastructure
│   ├── base/                    # Generic Abstract Classes & Interfaces
│   │   ├── BaseEntity.java
│   │   ├── BaseAuditEntity.java
│   │   ├── BaseController.java
│   │   ├── BaseService.java
│   │   ├── BaseServiceImpl.java
│   │   ├── BaseRepository.java
│   │   ├── BaseMapper.java
│   │   └── BaseResponse.java
│   ├── exception/               # Global Exceptions & Handlers
│   │   ├── BaseException.java
│   │   ├── ResourceNotFoundException.java
│   │   ├── BusinessRuleViolationException.java
│   │   ├── UnauthorizedException.java
│   │   └── GlobalExceptionHandler.java
│   ├── logging/                 # JSON Structured Logging & MDC Filters
│   │   ├── CorrelationIdFilter.java
│   │   ├── RequestLoggingFilter.java
│   │   └── LoggingAspect.java
│   ├── security/                # JWT & Dynamic RBAC Engine
│   │   ├── JwtTokenProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   ├── RedisTokenBlacklistService.java
│   │   ├── DynamicPermissionEvaluator.java
│   │   ├── RateLimitingFilter.java
│   │   └── SecurityConfig.java
│   ├── notification/            # Strategy Pattern Notification Engine
│   │   ├── NotificationProvider.java
│   │   ├── EmailProvider.java
│   │   ├── SmsProvider.java
│   │   ├── WhatsappProvider.java
│   │   ├── PushNotificationProvider.java
│   │   └── InAppNotificationProvider.java
│   └── storage/                 # Storage Abstraction Strategy
│       ├── StorageProvider.java
│       ├── LocalStorageProvider.java
│       ├── MinioStorageProvider.java
│       └── S3StorageProvider.java
└── modules/                     # Out-of-the-Box Core Modules
    ├── auth/                    # Login, Logout, Refresh, Password Management
    ├── user/                    # User CRUD & State Management
    ├── role/                    # Role & Fine-Grained Permission Assignment
    ├── org/                     # Organization, Branch, Department Hierarchy
    ├── audit/                   # Automated Entity & API Audit Logs
    ├── setting/                 # Key-Value Master Data Caching
    └── dashboard/               # System Health & Analytics Summary
```

---

## 🏛️ 2. Core Base Classes

### `BaseEntity.java`
```java
package com.dasp.framework.core.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;
}
```

### `BaseAuditEntity.java`
```java
package com.dasp.framework.core.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity extends BaseEntity {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
```

---

## 🔐 3. Security & Dynamic RBAC Engine

The framework implements fine-grained Dynamic Role-Based Access Control (RBAC) backed by Redis caching:

```text
User  --> UserRoles  --> Roles  --> RolePermissions  --> Permissions (e.g. Customer.Create)
                                                             │
                                                             ▼ Cached in Redis
                                                    JwtAuthenticationFilter
                                                             │
                                                             ▼ Evaluated via
                                             @PreAuthorize("hasAuthority('Customer.Create')")
```

### Redis Token Blacklist
Upon logout or password reset, JWT tokens (identified by `jti` claim) are immediately stored in Redis with an expiration equal to the remaining JWT lifetime.

```java
public void blacklistToken(String tokenId, long remainingExpirationMs) {
    redisTemplate.opsForValue().set(
        "jwt:blacklist:" + tokenId, 
        "true", 
        Duration.ofMillis(remainingExpirationMs)
    );
}
```

---

## 🔔 4. Notification Strategy Pattern

Modules send notifications strictly via the vendor-agnostic `NotificationProvider` strategy pattern:

```java
public interface NotificationProvider {
    NotificationChannel getChannel();
    NotificationResult send(NotificationRequest request);
}
```

Supported Providers out of the box:
- `EmailProvider`: SMTP with Thymeleaf HTML engine & attachment support.
- `SmsProvider`: Transactional & OTP SMS integration contract.
- `WhatsappProvider`: Meta Cloud API template & media message ready.
- `PushNotificationProvider`: Firebase Cloud Messaging (FCM) integration.
- `InAppNotificationProvider`: Real-time web socket notification dispatch and database persistence.

---

## 📊 5. Structured JSON Logging & MDC Context

All logs output structured JSON with mandatory MDC trace identifiers:

```json
{
  "@timestamp": "2026-07-20T21:50:00.123+05:30",
  "log.level": "INFO",
  "traceId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "correlationId": "corr-88492042",
  "requestId": "req-19204",
  "service.name": "dasp-backend",
  "logger": "com.dasp.framework.security.JwtAuthenticationFilter",
  "message": "User admin@dasp.com authenticated successfully",
  "executionTimeMs": 14
}
```

---

## ⚙️ 6. Redis Infrastructure Support

Redis is used for 6 critical platform features:
1. **JWT Blacklisting**: Instant token invalidation upon logout.
2. **Dynamic RBAC Cache**: User permissions stored under `rbac:user:{userId}:permissions`.
3. **Master Data Cache**: Settings and configuration cached under `settings:key:{key}`.
4. **Rate Limiting**: Sliding window counter under `ratelimit:{ip}:{endpoint}`.
5. **OTP Storage**: Temporary OTP generation with automatic TTL.
6. **API Response Caching**: Fast retrieval for idempotent GET queries.
