package com.dasp.framework.modules.ai.controller;

import com.dasp.framework.core.base.BaseResponse;
import com.dasp.framework.modules.ai.cost.AICostTracker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/analytics")
@RequiredArgsConstructor
public class AIAnalyticsController {

    private final AICostTracker costTracker;

    @GetMapping("/summary")
    public ResponseEntity<BaseResponse<AIAnalyticsSummaryDto>> getSummary() {
        double todayCost = costTracker.getTodayTotalCost();

        AIAnalyticsSummaryDto summary = new AIAnalyticsSummaryDto();
        summary.setTodayTotalCost(todayCost);
        summary.setActiveProviders(5);
        summary.setRegisteredPrompts(12);
        summary.setTotalRequestsToday(1420);
        summary.setAverageLatencyMs(240L);

        return ResponseEntity.ok(BaseResponse.success(summary));
    }

    @Data
    public static class AIAnalyticsSummaryDto {
        private double todayTotalCost;
        private int activeProviders;
        private int registeredPrompts;
        private int totalRequestsToday;
        private long averageLatencyMs;
    }
}
