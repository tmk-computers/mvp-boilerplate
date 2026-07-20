package com.dasp.framework.modules.ai.cost;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AICostTracker {

    private final StringRedisTemplate redisTemplate;
    private static final String DAILY_COST_KEY_PREFIX = "ai:cost:daily:";

    public void trackCost(String providerName, double cost) {
        String todayKey = DAILY_COST_KEY_PREFIX + LocalDate.now().toString();
        redisTemplate.opsForValue().increment(todayKey, cost);
        log.info("Tracked AI usage cost: ${} for provider {}", cost, providerName);
    }

    public double getTodayTotalCost() {
        String todayKey = DAILY_COST_KEY_PREFIX + LocalDate.now().toString();
        String val = redisTemplate.opsForValue().get(todayKey);
        return val != null ? Double.parseDouble(val) : 0.0;
    }
}
