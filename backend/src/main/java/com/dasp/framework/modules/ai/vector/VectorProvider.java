package com.dasp.framework.modules.ai.vector;

import java.util.List;
import java.util.Map;

public interface VectorProvider {

    String getProviderName();

    void storeVector(String collectionName, String documentId, List<Float> vector, Map<String, Object> metadata);

    List<SearchResult> searchSimilarity(String collectionName, List<Float> queryVector, int topK);

    void deleteVector(String collectionName, String documentId);

    record SearchResult(String documentId, double score, Map<String, Object> metadata, String content) {}
}
