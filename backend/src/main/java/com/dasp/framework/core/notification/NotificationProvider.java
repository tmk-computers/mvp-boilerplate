package com.dasp.framework.core.notification;

public interface NotificationProvider {

    NotificationChannel getChannel();

    NotificationResult send(NotificationRequest request);
}
