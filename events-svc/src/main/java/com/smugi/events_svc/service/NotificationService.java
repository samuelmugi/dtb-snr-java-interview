package com.smugi.events_svc.service;

import com.smugi.events_svc.model.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendEmail(NotificationEvent event) {
        log.info("📧 Mock Email sent for txn: {}", event.getTransactionId());
    }

    public void sendSms(NotificationEvent event) {
        log.info("📱 Mock SMS sent for txn: {}", event.getTransactionId());
    }
}
