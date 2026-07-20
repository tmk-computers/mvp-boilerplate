package com.dasp.framework.modules.ai.controller;

import com.dasp.framework.core.base.BaseResponse;
import com.dasp.framework.modules.ai.audit.AIAuditService;
import com.dasp.framework.modules.ai.cost.AICostTracker;
import com.dasp.framework.modules.ai.providers.AIProvider;
import com.dasp.framework.modules.ai.providers.AIProviderFactory;
import com.dasp.framework.modules.ai.providers.AIProviderType;
import com.dasp.framework.modules.ai.providers.AIRequest;
import com.dasp.framework.modules.ai.providers.AIResponse;
import com.dasp.framework.modules.ai.security.AISecurityService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIChatController {

    private final AIProviderFactory providerFactory;
    private final AISecurityService securityService;
    private final AIAuditService auditService;
    private final AICostTracker costTracker;

    @PostMapping("/chat")
    public ResponseEntity<BaseResponse<AIResponse>> chat(@Valid @RequestBody AIChatRequest request) {
        if (securityService.detectPromptInjection(request.getPrompt())) {
            return ResponseEntity.badRequest()
                    .body(BaseResponse.error("Security policy violation: Prompt Injection detected", "PROMPT_INJECTION_DETECTED"));
        }

        String sanitizedPrompt = securityService.maskPII(request.getPrompt());

        AIProviderType type = request.getProviderType() != null
                ? AIProviderType.valueOf(request.getProviderType().toUpperCase())
                : AIProviderType.OPENAI;

        AIProvider provider = providerFactory.getProvider(type);

        AIRequest aiRequest = AIRequest.builder()
                .prompt(sanitizedPrompt)
                .modelName(request.getModelName())
                .temperature(request.getTemperature())
                .maxTokens(request.getMaxTokens())
                .build();

        AIResponse response = provider.chat(aiRequest);

        auditService.logAIExecution("API_USER", response.getProviderName(), response.getModelName(),
                response.getPromptTokens(), response.getCompletionTokens(), response.getEstimatedCost(), response.getLatencyMs());

        costTracker.trackCost(response.getProviderName(), response.getEstimatedCost());

        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Data
    public static class AIChatRequest {
        private String prompt;
        private String providerType;
        private String modelName;
        private Double temperature;
        private Integer maxTokens;
    }
}
