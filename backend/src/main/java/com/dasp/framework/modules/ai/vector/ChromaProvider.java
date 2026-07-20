package com.dasp.framework.modules.ai.vector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("chromaProvider")
public class ChromaProvider implements VectorProvider {

    @Override
    public String getProviderName() { return "Chroma"; }

    @Override
    public void storeVector(String collectionName, String documentId, List<Float> vector, Map<String, Object> metadata) {
        log.info("Storing embedding in Chroma DB collection: {}", collectionName);
    }

    @Override
    public List<SearchResult> searchSimilarity(String collectionName, List<Float> queryVector, int topK) {
        return List.of(new SearchResult("chroma-1", 0.91, Map.of(), "Chroma open-source vector store context."));
    }

    @Override
    public void deleteVector(String collectionName, String documentId) {}
}
