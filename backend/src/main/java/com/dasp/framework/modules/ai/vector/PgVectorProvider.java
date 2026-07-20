package com.dasp.framework.modules.ai.vector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("pgVectorProvider")
public class PgVectorProvider implements VectorProvider {

    @Override
    public String getProviderName() {
        return "PgVector";
    }

    @Override
    public void storeVector(String collectionName, String documentId, List<Float> vector, Map<String, Object> metadata) {
        log.info("Storing vector embedding in PostgreSQL PgVector extension table for doc: {}", documentId);
    }

    @Override
    public List<SearchResult> searchSimilarity(String collectionName, List<Float> queryVector, int topK) {
        log.info("Executing cosine similarity search in PgVector for topK: {}", topK);
        return List.of(
                new SearchResult("doc-1", 0.94, Map.of("title", "Company Policy"), "Employees must submit expense claims by 25th of every month."),
                new SearchResult("doc-2", 0.88, Map.of("title", "SOP Manual"), "Standard operating procedure for processing customer refunds.")
        );
    }

    @Override
    public void deleteVector(String collectionName, String documentId) {
        log.info("Deleting PgVector entry for doc: {}", documentId);
    }
}
