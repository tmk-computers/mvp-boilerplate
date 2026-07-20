# Autonomous AI Agent Framework (`AGENTS.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Architecture: Goal-Oriented AI Agents | Tool Execution | Task Automation

---

## 🤖 1. Agent Architecture

An AI Agent is a goal-oriented autonomous entity equipped with instructions, context memory, and access to registered business tools (`AITool`).

```text
       ┌────────────────────────┐
       │   Agent Goal & Task    │
       └───────────┬────────────┘
                   │
                   ▼
       ┌────────────────────────┐
       │ Agent Execution Engine │
       └───────────┬────────────┘
                   │
         ┌─────────┴─────────┐
         ▼                   ▼
  ┌──────────────┐    ┌──────────────┐
  │  Memory /    │    │ Available    │
  │ Context      │    │ Tools        │
  └──────────────┘    └──────┬───────┘
                             │
     ┌──────────┬────────────┼───────────┬──────────┐
     ▼          ▼            ▼           ▼          ▼
  DB Search   REST API     Email/SMS   Calculator   OCR
```

---

## 🛠️ 2. `AITool` Contract Interface

```java
public interface AITool {
    String getName();
    String describe();
    boolean validate(Map<String, Object> input);
    ToolResult execute(Map<String, Object> input);
    record ToolResult(boolean success, Object data, String error) {}
}
```
