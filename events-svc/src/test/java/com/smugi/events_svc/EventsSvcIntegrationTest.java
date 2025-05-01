package com.smugi.events_svc;

import com.smugi.events_svc.model.NotificationEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class EventsSvcIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Test
    void publishMockTransactionEvent() throws Exception {
        NotificationEvent event = new NotificationEvent();
        event.setTransactionId(UUID.randomUUID());
        event.setFromAccountId(UUID.randomUUID());
        event.setToAccountId(UUID.randomUUID());
        event.setAmount(BigDecimal.valueOf(2500));
        event.setType("TRANSFER");
        event.setStatus("SUCCESS");
        event.setCreatedAt(LocalDateTime.now());

        String json = objectMapper.writeValueAsString(event);
        kafkaTemplate.send("transaction-events", json);

        // No assertion here; success is no exception thrown while publishing
    }
}
