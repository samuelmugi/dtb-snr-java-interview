package com.smugi.payment_svc.service;

import com.smugi.payment_svc.dto.TransactionRequest;
import com.smugi.payment_svc.model.Transaction;
import com.smugi.payment_svc.producer.TransactionEventProducer;
import com.smugi.payment_svc.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionEventProducer producer;

    @InjectMocks
    private PaymentService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessTransaction() {
        TransactionRequest request = new TransactionRequest();
        request.setFromAccountId(UUID.randomUUID());
        request.setToAccountId(UUID.randomUUID());
        request.setAmount(BigDecimal.valueOf(1000));
        request.setType("TOPUP");

        when(repository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.process(request))
            .expectNextMatches(response -> response.getAmount().compareTo(BigDecimal.valueOf(1000)) == 0)
            .verifyComplete();

        verify(producer, times(1)).publish(any(Transaction.class));
    }
}
