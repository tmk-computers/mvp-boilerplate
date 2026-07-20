package com.dasp.framework.modules.ai.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class AISecurityService {

    private static final Pattern INJECTION_PATTERN = Pattern.compile(
            "(?i)(ignore previous instructions|system prompt|override safety|jailbreak|disregard prior)",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PII_EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private static final Pattern PII_PHONE_PATTERN = Pattern.compile("\\b\\d{10}\\b|\\+\\d{1,3}[-.\\s]?\\d{10}\\b");

    public boolean detectPromptInjection(String prompt) {
        if (prompt == null) return false;
        boolean matches = INJECTION_PATTERN.matcher(prompt).find();
        if (matches) {
            log.warn("Security Alert: Potential Prompt Injection attempt detected!");
        }
        return matches;
    }

    public String maskPII(String text) {
        if (text == null) return "";
        String masked = PII_EMAIL_PATTERN.matcher(text).replaceAll("[REDACTED_EMAIL]");
        masked = PII_PHONE_PATTERN.matcher(masked).replaceAll("[REDACTED_PHONE]");
        return masked;
    }
}
