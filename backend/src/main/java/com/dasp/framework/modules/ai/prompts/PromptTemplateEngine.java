package com.dasp.framework.modules.ai.prompts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class PromptTemplateEngine {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{(\\w+)\\}\\}");

    public String render(String templateContent, Map<String, Object> variables) {
        if (templateContent == null) return "";
        if (variables == null || variables.isEmpty()) return templateContent;

        StringBuilder sb = new StringBuilder();
        Matcher matcher = VARIABLE_PATTERN.matcher(templateContent);

        while (matcher.find()) {
            String varName = matcher.group(1);
            Object replacement = variables.getOrDefault(varName, "{{" + varName + "}}");
            matcher.appendReplacement(sb, Matcher.quoteReplacement(String.valueOf(replacement)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
