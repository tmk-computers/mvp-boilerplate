package com.dasp.framework.modules.ai.agent;

import com.dasp.framework.modules.ai.providers.AIProvider;
import com.dasp.framework.modules.ai.providers.AIProviderFactory;
import com.dasp.framework.modules.ai.providers.AIRequest;
import com.dasp.framework.modules.ai.providers.AIResponse;
import com.dasp.framework.modules.ai.tools.AITool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentExecutionEngine {

    private final AIProviderFactory providerFactory;
    private final List<AITool> availableTools;

    public String executeGoal(String agentGoal, String userPrompt) {
        log.info("Agent starting goal execution: {}", agentGoal);
        AIProvider provider = providerFactory.getDefaultProvider();

        AIRequest request = AIRequest.builder()
                .systemInstruction("You are an autonomous AI Agent with goal: " + agentGoal)
                .prompt(userPrompt)
                .build();

        AIResponse response = provider.chat(request);
        log.info("Agent goal execution finished successfully");
        return response.getText();
    }
}
