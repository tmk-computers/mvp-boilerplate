# REST API Reference - AI Module (`API-AI.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Endpoints: `/api/v1/ai/*`

---

## 📡 1. Endpoints Overview

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/ai/chat` | Submit prompt to active AI Provider |
| `GET` | `/api/v1/ai/analytics/summary` | Fetch AI cost, token, and latency metrics |
| `GET` | `/api/v1/ai/providers` | List configured AI LLM providers |
| `POST` | `/api/v1/ai/prompts` | Create new versioned prompt template |

---

## 📬 2. Chat API Request Example

```http
POST /api/v1/ai/chat
Content-Type: application/json
Authorization: Bearer <JWT_ACCESS_TOKEN>

{
  "prompt": "Summarize customer feedback for ticket #9102",
  "providerType": "OPENAI",
  "modelName": "gpt-4o",
  "temperature": 0.7,
  "maxTokens": 500
}
```
