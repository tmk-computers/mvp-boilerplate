package com.dasp.framework.modules.ai.rag;

import com.dasp.framework.modules.ai.providers.AIProvider;
import com.dasp.framework.modules.ai.providers.AIProviderFactory;
import com.dasp.framework.modules.ai.vector.VectorProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RAGPipelineService {

    private final AIProviderFactory providerFactory;
    private final DocumentChunkerService chunkerService;
    
    @Qualifier("pgVectorProvider")
    private final VectorProvider vectorProvider;

    public void ingestDocument(String collectionName, String documentTitle, String content) {
        log.info("Processing RAG ingestion for document: {}", documentTitle);
        List<String> chunks = chunkerService.chunkText(content, 500, 50);
        AIProvider aiProvider = providerFactory.getDefaultProvider();

        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            List<Float> embedding = aiProvider.embed(chunk);
            String chunkId = UUID.randomUUID().toString();
            vectorProvider.storeVector(collectionName, chunkId, embedding, Map.of(
                    "title", documentTitle,
                    "chunkIndex", i,
                    "content", chunk
            ));
        }
        log.info("RAG ingestion complete. Indexed {} chunks in collection {}", chunks.size(), collectionName);
    }

    public String assembleContext(String collectionName, String query, int topK) {
        AIProvider aiProvider = providerFactory.getDefaultProvider();
        List<Float> queryEmbedding = aiProvider.embed(query);
        List<VectorProvider.SearchResult> results = vectorProvider.searchSimilarity(collectionName, queryEmbedding, topK);

        return results.stream()
                .map(VectorProvider.SearchResult::content)
                .collect(Collectors.joining("\n---\n"));
    }
}
