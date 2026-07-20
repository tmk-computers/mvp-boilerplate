# Security Architecture Specification (`SECURITY.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Security Framework: Spring Security 6.x | OAuth2/JWT | Redis Token Blacklist | OWASP Safeguards

---

## 🔒 1. Authentication & JWT Life Cycle

1. **Access Token**: Short-lived (e.g., 15 minutes) signed JWT containing User ID, Tenant ID, and assigned authorities.
2. **Refresh Token**: Long-lived (e.g., 7 days) securely hashed token stored in database/Redis.
3. **Instant Token Invalidation**: When a user logs out, the active `jti` is added to Redis blacklist (`jwt:blacklist:<jti>`) with remaining TTL.
4. **Password Encryption**: Passwords are hashed using BCrypt with strength factor 12.

---

## 🛡️ 2. Dynamic RBAC Engine

Permissions are dynamically evaluated per request:

```java
@GetMapping("/{id}")
@PreAuthorize("hasAuthority('Customer.View')")
public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(@PathVariable UUID id) {
    return ResponseEntity.ok(ApiResponse.success(customerService.findById(id)));
}
```

Permissions are loaded from the database into Redis on user login and invalidated whenever user roles/permissions are modified.

---

## 🛑 3. OWASP Safeguards & Security Headers

1. **SQL Injection Prevention**: Exclusively use JPA Parameterized queries or Spring Data methods.
2. **Rate Limiting Filter**: IP and User-based sliding window rate limiter backed by Redis.
3. **XSS & Security Headers**:
   - `X-Content-Type-Options: nosniff`
   - `X-Frame-Options: DENY`
   - `X-XSS-Protection: 1; mode=block`
   - `Strict-Transport-Security: max-age=31536000; includeSubDomains`
   - `Content-Security-Policy: default-src 'self'`
4. **CORS Policy**: Restrict origin headers explicitly via environment configuration (`ALLOWED_ORIGINS`).
