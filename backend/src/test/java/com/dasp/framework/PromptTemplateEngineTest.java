package com.dasp.framework;

import com.dasp.framework.modules.ai.prompts.PromptTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromptTemplateEngineTest {

    private final PromptTemplateEngine engine = new PromptTemplateEngine();

    @Test
    void render_ShouldReplaceVariablesCorrectly() {
        String template = "Hello {{customerName}}, your invoice {{invoiceNumber}} is ready.";
        Map<String, Object> variables = Map.of(
                "customerName", "Acme Corp",
                "invoiceNumber", "INV-2026-001"
        );

        String result = engine.render(template, variables);
        assertEquals("Hello Acme Corp, your invoice INV-2026-001 is ready.", result);
    }
}
