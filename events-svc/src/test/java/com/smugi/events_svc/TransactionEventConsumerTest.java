package com.smugi.events_svc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smugi.events_svc.consumer.TransactionEventConsumer;
import com.smugi.events_svc.model.NotificationEvent;
import com.smugi.events_svc.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

class TransactionEventConsumerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionEventConsumer consumer;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // ✅ Required!
        consumer = new TransactionEventConsumer(objectMapper, notificationService);
    }

    @Test
    void testListen_shouldCallEmailAndSms() throws Exception {
        NotificationEvent event = NotificationEvent.builder()
                .transactionId(UUID.randomUUID())
                .amount(BigDecimal.valueOf(500))
                .type("TRANSFER")
                .fromAccountId(UUID.randomUUID())
                .toAccountId(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .build();

        String json = objectMapper.writeValueAsString(event);

        consumer.listen(json);

        verify(notificationService, times(1)).sendEmail(any(NotificationEvent.class));
        verify(notificationService, times(1)).sendSms(any(NotificationEvent.class));
    }
}
