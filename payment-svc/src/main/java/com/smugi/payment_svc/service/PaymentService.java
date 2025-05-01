package com.smugi.payment_svc.service;

import com.smugi.payment_svc.dto.TransactionRequest;
import com.smugi.payment_svc.dto.TransactionResponse;
import com.smugi.payment_svc.model.Transaction;
import com.smugi.payment_svc.repository.TransactionRepository;
import com.smugi.payment_svc.producer.TransactionEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository repository;
    private final TransactionEventProducer producer;

    public Mono<TransactionResponse> process(TransactionRequest request) {
        Transaction txn = Transaction.builder()
                .id(UUID.randomUUID())
                .fromAccountId(request.getFromAccountId())
                .toAccountId(request.getToAccountId())
                .amount(request.getAmount())
                .type(request.getType())
                .status("SUCCESS")
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(txn)
                .doOnSuccess(producer::publish)
                .map(saved -> TransactionResponse.builder()
                        .id(saved.getId())
                        .fromAccountId(saved.getFromAccountId())
                        .toAccountId(saved.getToAccountId())
                        .amount(saved.getAmount())
                        .type(saved.getType())
                        .status(saved.getStatus())
                        .createdAt(saved.getCreatedAt())
                        .build());
    }
}
