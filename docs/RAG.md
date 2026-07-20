# RAG & Vector Database Specification (`RAG.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Architecture: Retrieval Augmented Generation | Vector Database Abstraction

---

## 🔍 1. RAG Workflow Architecture

```text
Document (PDF, Word, CSV, Images)
              │
              ▼
  Text Extraction & OCR Engine
              │
              ▼
    Document Chunking Engine (Fixed Window + Overlap)
              │
              ▼
  Embedding Generation (AIProvider.embed())
              │
              ▼
  Vector Database Storage (PgVector / Qdrant / Milvus / Pinecone / Chroma)
              │
              ▼
  User Query ──> Cosine Similarity Search ──> Context Assembly ──> LLM Response
```

---

## 🗄️ 2. Vector Database Abstraction (`VectorProvider`)

Supported Vector Store implementations out of the box:
1. **PgVector**: PostgreSQL native vector extension. Ideal for zero additional infrastructure overhead.
2. **Qdrant**: High-performance gRPC vector database with filtering payload support.
3. **Milvus**: Distributed enterprise vector database for massive billion-scale vector indexes.
4. **Pinecone**: Fully managed cloud vector database.
5. **Chroma**: Open-source lightweight developer vector database.
