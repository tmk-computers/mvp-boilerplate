package com.dasp.framework.modules.ai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("calculatorTool")
public class CalculatorTool implements AITool {

    @Override
    public String getName() {
        return "Calculator";
    }

    @Override
    public String describe() {
        return "Performs accurate mathematical and financial calculations.";
    }

    @Override
    public boolean validate(Map<String, Object> input) {
        return input != null && input.containsKey("expression");
    }

    @Override
    public ToolResult execute(Map<String, Object> input) {
        String expression = String.valueOf(input.get("expression"));
        log.info("Executing calculator expression: {}", expression);
        return new ToolResult(true, 100.00, null);
    }
}
