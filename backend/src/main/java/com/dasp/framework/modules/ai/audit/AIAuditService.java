package com.dasp.framework.modules.ai.audit;

import com.dasp.framework.modules.ai.providers.AIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AIAuditService {

    public void logAIExecution(String username, String provider, String model, int promptTokens, int completionTokens, double cost, long latencyMs) {
        log.info("AI Audit Log -> User: {}, Provider: {}, Model: {}, Tokens: {} (Prompt: {}, Completion: {}), Cost: ${}, Latency: {}ms",
                username, provider, model, (promptTokens + completionTokens), promptTokens, completionTokens, cost, latencyMs);
    }
}
