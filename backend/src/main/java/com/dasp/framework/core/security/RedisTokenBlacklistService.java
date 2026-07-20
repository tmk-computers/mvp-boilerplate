package com.dasp.framework.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisTokenBlacklistService {

    private final StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    public void blacklistToken(String tokenId, long remainingExpirationMs) {
        if (remainingExpirationMs > 0) {
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + tokenId,
                    "true",
                    Duration.ofMillis(remainingExpirationMs)
            );
        }
    }

    public boolean isBlacklisted(String tokenId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + tokenId));
    }
}
