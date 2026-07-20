package com.dasp.framework.modules.ai.providers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRequest {

    private String prompt;
    private String systemInstruction;
    private String modelName;
    private Double temperature;
    private Integer maxTokens;
    private Double topP;
    private Map<String, Object> parameters;
    private String mediaUrl;
    private String sourceLanguage;
    private String targetLanguage;
}
