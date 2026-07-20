# REST API Design Standards (`API-STANDARDS.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Specification: RESTful Level 3 | Response Envelope | RFC 7807 Problem Details

---

## 📬 1. Standard Response Envelope (`ApiResponse<T>`)

Every API endpoint MUST return a consistent JSON response envelope:

```json
{
  "success": true,
  "message": "Customer created successfully",
  "data": {
    "id": "c1f7b764-8842-491b-871d-192a71d2b8b9",
    "name": "Acme Corp",
    "email": "contact@acme.com",
    "createdDate": "2026-07-20T21:50:00Z"
  },
  "timestamp": "2026-07-20T21:50:00Z",
  "traceId": "trace-9182374"
}
```

---

## 📑 2. Standard Paginated Response Envelope

```json
{
  "success": true,
  "message": "Resource list retrieved successfully",
  "data": {
    "content": [ ... ],
    "pageNumber": 0,
    "pageSize": 10,
    "totalElements": 45,
    "totalPages": 5,
    "isLast": false
  },
  "timestamp": "2026-07-20T21:50:00Z",
  "traceId": "trace-9182374"
}
```

---

## ❌ 3. Standard Error Response

```json
{
  "success": false,
  "message": "Validation failed for request parameters",
  "errorCode": "VALIDATION_ERROR",
  "errors": [
    {
      "field": "email",
      "rejectedValue": "invalid-email",
      "message": "Must be a valid email address"
    }
  ],
  "timestamp": "2026-07-20T21:50:00Z",
  "traceId": "trace-9182374"
}
```

---

## 🔄 4. Versioning & Idempotency

1. **API Versioning**: Prefix all business routes with `/api/v1/`.
2. **Idempotency Keys**: For sensitive POST operations (payments, invoices), supply `X-Idempotency-Key: <UUID>` header to prevent duplicate processing.
