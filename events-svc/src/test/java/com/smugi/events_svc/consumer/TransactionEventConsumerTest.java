package com.smugi.events_svc.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smugi.events_svc.model.NotificationEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TransactionEventConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TransactionEventConsumer consumer;

    public TransactionEventConsumerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listen_shouldParseMessageAndLog() throws Exception {
        String message = "{\"transactionId\":\"123e4567-e89b-12d3-a456-426614174000\",\"fromAccountId\":\"111e4567-e89b-12d3-a456-426614174000\",\"toAccountId\":\"222e4567-e89b-12d3-a456-426614174000\",\"amount\":100.00,\"type\":\"TRANSFER\",\"status\":\"SUCCESS\",\"createdAt\":\"2024-01-01T00:00:00\"}";

        NotificationEvent event = new NotificationEvent();
        event.setType("TRANSFER");

        when(objectMapper.readValue(eq(message), eq(NotificationEvent.class))).thenReturn(event);

        consumer.listen(message);

        verify(objectMapper).readValue(eq(message), eq(NotificationEvent.class));
    }
}
