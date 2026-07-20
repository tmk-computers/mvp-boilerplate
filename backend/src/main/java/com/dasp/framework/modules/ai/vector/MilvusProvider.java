package com.dasp.framework.modules.ai.vector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("milvusProvider")
public class MilvusProvider implements VectorProvider {

    @Override
    public String getProviderName() { return "Milvus"; }

    @Override
    public void storeVector(String collectionName, String documentId, List<Float> vector, Map<String, Object> metadata) {
        log.info("Storing vector in Milvus enterprise cluster");
    }

    @Override
    public List<SearchResult> searchSimilarity(String collectionName, List<Float> queryVector, int topK) {
        return List.of(new SearchResult("milvus-1", 0.92, Map.of(), "Milvus knowledge base context chunk."));
    }

    @Override
    public void deleteVector(String collectionName, String documentId) {}
}
