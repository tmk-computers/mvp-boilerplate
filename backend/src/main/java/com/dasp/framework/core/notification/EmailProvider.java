package com.dasp.framework.core.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailProvider implements NotificationProvider {

    private final JavaMailSender mailSender;

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(request.getRecipient());
            helper.setSubject(request.getSubject());
            helper.setText(request.getContent(), true);

            mailSender.send(message);
            log.info("Email notification sent successfully to: {}", request.getRecipient());

            return NotificationResult.builder()
                    .success(true)
                    .messageId(UUID.randomUUID().toString())
                    .channel(getChannel())
                    .build();
        } catch (Exception e) {
            log.error("Failed to send email notification to: {}", request.getRecipient(), e);
            return NotificationResult.builder()
                    .success(false)
                    .errorMessage(e.getMessage())
                    .channel(getChannel())
                    .build();
        }
    }
}
