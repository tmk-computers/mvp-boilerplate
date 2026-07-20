package com.dasp.framework.modules.ai.tools;

import java.util.Map;

public interface AITool {

    String getName();

    String describe();

    boolean validate(Map<String, Object> input);

    ToolResult execute(Map<String, Object> input);

    record ToolResult(boolean success, Object data, String error) {}
}
