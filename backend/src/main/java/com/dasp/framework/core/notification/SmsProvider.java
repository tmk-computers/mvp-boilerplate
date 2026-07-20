package com.dasp.framework.core.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SmsProvider implements NotificationProvider {

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        log.info("Sending SMS notification to: {}", request.getRecipient());
        return NotificationResult.builder()
                .success(true)
                .messageId(UUID.randomUUID().toString())
                .channel(getChannel())
                .build();
    }
}
