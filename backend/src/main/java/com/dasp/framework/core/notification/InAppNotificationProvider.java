package com.dasp.framework.core.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class InAppNotificationProvider implements NotificationProvider {

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.IN_APP;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        log.info("Persisting In-App Notification bell alert for user: {}", request.getRecipient());
        return NotificationResult.builder()
                .success(true)
                .messageId(UUID.randomUUID().toString())
                .channel(getChannel())
                .build();
    }
}
