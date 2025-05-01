package com.smugi.payment_svc.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smugi.payment_svc.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(Transaction txn) {
        try {
            String message = objectMapper.writeValueAsString(txn);
            kafkaTemplate.send("transaction-events", message);
            log.info("📤 Kafka message published for txn: {}", txn.getId());
        } catch (Exception e) {
            log.error("❌ Kafka publishing failed", e);
        }
    }
}
