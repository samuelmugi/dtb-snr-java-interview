package com.smugi.events_svc.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smugi.events_svc.model.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "transaction-events", groupId = "events-svc")
    public void listen(String message) {
        try {
            NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);
            log.info("📩 Notification triggered for transaction: {}", event);
            // Here you'd mock email/SMS sending
        } catch (Exception e) {
            log.error("Failed to process transaction event", e);
        }
    }
}
