package com.dasp.framework.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;
    private static final int MAX_REQUESTS_PER_MINUTE = 100;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        String key = "ratelimit:" + clientIp;

        Long currentCount = redisTemplate.opsForValue().increment(key);
        if (currentCount != null && currentCount == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }

        if (currentCount != null && currentCount > MAX_REQUESTS_PER_MINUTE) {
            log.warn("Rate limit exceeded for client IP: {}", clientIp);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Try again in a minute.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
