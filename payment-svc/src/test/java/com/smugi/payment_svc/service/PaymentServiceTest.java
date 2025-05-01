package com.smugi.payment_svc.service;

import com.smugi.payment_svc.dto.TransactionRequest;
import com.smugi.payment_svc.model.Transaction;
import com.smugi.payment_svc.repository.TransactionRepository;
import com.smugi.payment_svc.dto.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private PaymentService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process_shouldReturnSuccessTransactionResponse() {
        TransactionRequest request = new TransactionRequest();
        request.setFromAccountId(UUID.randomUUID());
        request.setToAccountId(UUID.randomUUID());
        request.setAmount(BigDecimal.valueOf(1500));
        request.setType("TRANSFER");

        Transaction mockTxn = Transaction.builder()
            .id(UUID.randomUUID())
            .fromAccountId(request.getFromAccountId())
            .toAccountId(request.getToAccountId())
            .amount(request.getAmount())
            .type(request.getType())
            .status("SUCCESS")
            .createdAt(LocalDateTime.now())
            .build();

        when(repository.save(any(Transaction.class))).thenReturn(Mono.just(mockTxn));

        StepVerifier.create(service.process(request))
            .expectNextMatches(response ->
                response.getAmount().compareTo(request.getAmount()) == 0 &&
                response.getType().equals("TRANSFER") &&
                response.getStatus().equals("SUCCESS")
            )
            .verifyComplete();
    }
}
