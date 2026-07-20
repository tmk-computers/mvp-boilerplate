package com.dasp.framework.core.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResult {

    private boolean success;
    private String messageId;
    private String errorMessage;
    private NotificationChannel channel;
}
