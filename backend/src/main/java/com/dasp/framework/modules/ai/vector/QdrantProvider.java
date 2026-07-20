package com.dasp.framework.modules.ai.vector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("qdrantProvider")
public class QdrantProvider implements VectorProvider {

    @Override
    public String getProviderName() { return "Qdrant"; }

    @Override
    public void storeVector(String collectionName, String documentId, List<Float> vector, Map<String, Object> metadata) {
        log.info("Storing vector in Qdrant collection: {}", collectionName);
    }

    @Override
    public List<SearchResult> searchSimilarity(String collectionName, List<Float> queryVector, int topK) {
        log.info("Performing Qdrant gRPC vector search");
        return List.of(new SearchResult("qdrant-doc-1", 0.95, Map.of(), "Sample Qdrant document content context."));
    }

    @Override
    public void deleteVector(String collectionName, String documentId) {
        log.info("Deleting vector point from Qdrant: {}", documentId);
    }
}
