package com.dasp.framework.modules.ai.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AIProviderFactory {

    private final Map<AIProviderType, AIProvider> providerMap;

    public AIProviderFactory(List<AIProvider> providers) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(AIProvider::getProviderType, Function.identity()));
    }

    public AIProvider getProvider(AIProviderType type) {
        AIProvider provider = providerMap.get(type);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported AI Provider Type: " + type);
        }
        return provider;
    }

    public AIProvider getDefaultProvider() {
        return getProvider(AIProviderType.OPENAI);
    }
}
