package com.dasp.framework.modules.ai.providers;

import java.util.List;

public interface AIProvider {

    AIProviderType getProviderType();

    AIResponse chat(AIRequest request);

    AIResponse generate(AIRequest request);

    AIResponse summarize(AIRequest request);

    AIResponse classify(AIRequest request);

    AIResponse extract(AIRequest request);

    AIResponse translate(AIRequest request);

    List<Float> embed(String text);

    AIResponse vision(AIRequest request);

    AIResponse speechToText(byte[] audioData);

    byte[] textToSpeech(String text);

    AIResponse imageGeneration(String prompt);

    AIResponse moderate(String text);

    int tokenCount(String text);

    double estimateCost(int promptTokens, int completionTokens);

    boolean health();
}
