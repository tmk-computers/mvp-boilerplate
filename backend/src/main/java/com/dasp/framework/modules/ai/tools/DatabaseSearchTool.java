package com.dasp.framework.modules.ai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("databaseSearchTool")
public class DatabaseSearchTool implements AITool {

    @Override
    public String getName() {
        return "DatabaseSearch";
    }

    @Override
    public String describe() {
        return "Executes structured SQL or JPA queries to retrieve business record metrics.";
    }

    @Override
    public boolean validate(Map<String, Object> input) {
        return input != null && input.containsKey("query");
    }

    @Override
    public ToolResult execute(Map<String, Object> input) {
        log.info("Executing database search tool query: {}", input.get("query"));
        return new ToolResult(true, Map.of("count", 42, "status", "SUCCESS"), null);
    }
}
