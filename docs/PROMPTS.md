# Prompt & Template Engine Specification (`PROMPTS.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Architecture: DB-Driven Prompt Management | Version Control | Variable Interpolation

---

## 📝 1. Prompt Management Philosophy

1. **Zero Hardcoded Prompts**: Prompts are stored in the database and managed via administrative UI.
2. **Variable Interpolation**: Dynamic variables enclosed in `{{varName}}` (e.g. `{{customerName}}`, `{{invoiceNumber}}`) are injected at runtime by `PromptTemplateEngine`.
3. **Version Control & Rollback**: Every edit creates a new `PromptVersion`. Rollback to previous prompt versions is supported with zero downtime.

---

## ⚙️ 2. Template Interpolation Syntax

```text
System Instruction: You are a customer support executive for DASP Digital.
Prompt: Hello {{customerName}}, your order {{orderNumber}} has been processed. Total amount: {{totalAmount}}.
```
