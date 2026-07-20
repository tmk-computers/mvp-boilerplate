# AI Security, Audit & Governance (`SECURITY-AI.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Architecture: Prompt Injection Defense | PII Detection & Masking | AI Cost & Token Governance

---

## 🔒 1. Prompt Injection Protection

All incoming prompt inputs are scanned prior to LLM dispatch for malicious override patterns (e.g., `"ignore previous instructions"`, `"override safety"`). Suspicious inputs trigger an immediate `PROMPT_INJECTION_DETECTED` HTTP 400 error.

---

## 🙈 2. PII Detection & Sensitive Data Masking

Before sending text to third-party APIs (OpenAI/Anthropic), sensitive Personally Identifiable Information (PII) such as email addresses, phone numbers, and national IDs are automatically masked using `AISecurityService`:

- Email: `user@example.com` -> `[REDACTED_EMAIL]`
- Phone: `+1-555-0199` -> `[REDACTED_PHONE]`

---

## 📊 3. AI Cost & Audit Logs

Every AI execution records structured MDC audit logs tracking:
- Username, Department, Provider Name, Model Name
- Prompt Tokens, Completion Tokens, Total Tokens
- Estimated Financial Cost ($ USD)
- Execution Latency (ms)
