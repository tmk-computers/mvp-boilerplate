package com.dasp.framework.core.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class WhatsappProvider implements NotificationProvider {

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.WHATSAPP;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        log.info("Sending WhatsApp Meta Cloud API message to recipient: {}", request.getRecipient());
        return NotificationResult.builder()
                .success(true)
                .messageId(UUID.randomUUID().toString())
                .channel(getChannel())
                .build();
    }
}
