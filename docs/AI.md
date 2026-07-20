# Enterprise AI Framework Specification (`AI.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Architecture: AI Provider Abstraction | Multi-LLM Support | Enterprise AI Platform

---

## 🤖 1. Framework Architecture & Design Principles

The DASP Digital AI Framework acts as an enterprise abstraction layer between business application modules (CRM, ERP, Billing, HRMS, Hospital, etc.) and external AI LLM providers.

```text
 Presentation Layer (Web Dashboard & Mobile App)
                        │
                        ▼
   Business Module (CRM, ERP, Inventory, Billing)
                        │
                        ▼
   ┌─────────────────────────────────────────┐
   │         AI Framework Abstraction        │
   │  (AIProvider, PromptEngine, RAG, Agent) │
   └────────────────────┬────────────────────┘
                        │
                        ▼
  ┌───────────────────────────────────────────┐
  │              Provider Factory             │
  └───────┬──────────┬──────────┬───────────┬─┘
          │          │          │           │
          ▼          ▼          ▼           ▼
       OpenAI      Gemini     Claude      Ollama
```

### Key Principles
1. **Zero Provider Coupling**: Business services call `AIProvider.chat()` or `AIProvider.generate()`. They NEVER import OpenAI or Gemini SDKs directly.
2. **Runtime Provider Switching**: Changing LLM provider requires only configuration or database updates.
3. **Multimodal Capabilities**: Unified support for Text Chat, Embeddings, Vision Analysis, Speech-to-Text, Text-to-Speech, and Image Generation.

---

## 🛠️ 2. `AIProvider` Interface Contract

```java
public interface AIProvider {
    AIProviderType getProviderType();
    AIResponse chat(AIRequest request);
    AIResponse generate(AIRequest request);
    AIResponse summarize(AIRequest request);
    AIResponse classify(AIRequest request);
    AIResponse extract(AIRequest request);
    AIResponse translate(AIRequest request);
    List<Float> embed(String text);
    AIResponse vision(AIRequest request);
    AIResponse speechToText(byte[] audioData);
    byte[] textToSpeech(String text);
    AIResponse imageGeneration(String prompt);
    AIResponse moderate(String text);
    int tokenCount(String text);
    double estimateCost(int promptTokens, int completionTokens);
    boolean health();
}
```

---

## 🚀 3. Supported AI Providers

| Provider | Supported Models | Primary Use Case |
| :--- | :--- | :--- |
| **OpenAI** | `gpt-4o`, `gpt-4o-mini`, `text-embedding-3-small`, `dall-e-3`, `whisper-1` | High-reasoning chat, complex code & JSON extraction |
| **Google Gemini** | `gemini-1.5-pro`, `gemini-1.5-flash`, `text-embedding-004` | Ultra-large context window, multimodal vision |
| **Anthropic Claude**| `claude-3-5-sonnet`, `claude-3-haiku` | Nuanced writing, document comprehension |
| **Azure OpenAI** | `azure-gpt-4o` | Enterprise compliance, private network deployment |
| **Local Ollama** | `llama3.2`, `mistral`, `nomic-embed-text` | Zero-cost offline / air-gapped on-premise execution |
