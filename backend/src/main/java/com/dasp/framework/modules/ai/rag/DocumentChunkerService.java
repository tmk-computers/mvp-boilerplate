package com.dasp.framework.modules.ai.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DocumentChunkerService {

    public List<String> chunkText(String fullText, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        if (fullText == null || fullText.isBlank()) return chunks;

        int start = 0;
        while (start < fullText.length()) {
            int end = Math.min(start + chunkSize, fullText.length());
            chunks.add(fullText.substring(start, end));
            if (end == fullText.length()) break;
            start += (chunkSize - overlap);
        }
        return chunks;
    }
}
