package com.dasp.framework.core.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String recipient;
    private String subject;
    private String content;
    private String templateName;
    private Map<String, Object> templateModel;
    private NotificationChannel channel;
}
