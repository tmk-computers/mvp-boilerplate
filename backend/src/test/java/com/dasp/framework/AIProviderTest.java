package com.dasp.framework;

import com.dasp.framework.modules.ai.providers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AIProviderTest {

    private AIProviderFactory factory;

    @BeforeEach
    void setUp() {
        List<AIProvider> providers = List.of(
                new OpenAIProvider(),
                new GeminiProvider(),
                new ClaudeProvider(),
                new AzureOpenAIProvider(),
                new OllamaProvider()
        );
        factory = new AIProviderFactory(providers);
    }

    @Test
    void getProvider_OpenAI_ShouldReturnOpenAIProvider() {
        AIProvider provider = factory.getProvider(AIProviderType.OPENAI);
        assertNotNull(provider);
        assertEquals(AIProviderType.OPENAI, provider.getProviderType());
    }

    @Test
    void chat_OpenAI_ShouldReturnValidResponse() {
        AIProvider provider = factory.getProvider(AIProviderType.OPENAI);
        AIRequest request = AIRequest.builder().prompt("Explain quantum computing").build();
        AIResponse response = provider.chat(request);

        assertNotNull(response);
        assertEquals("OPENAI", response.getProviderName());
        assertTrue(response.getLatencyMs() >= 0);
    }

    @Test
    void embed_Gemini_ShouldReturnVector() {
        AIProvider provider = factory.getProvider(AIProviderType.GEMINI);
        List<Float> embedding = provider.embed("Sample text");
        assertNotNull(embedding);
        assertEquals(768, embedding.size());
    }
}
