package com.smugi.payment_svc.service;

import com.smugi.payment_svc.dto.TransactionRequest;
import com.smugi.payment_svc.dto.TransactionResponse;
import com.smugi.payment_svc.model.Transaction;
import com.smugi.payment_svc.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository repository;

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

        return repository.save(txn).map(this::toResponse);
    }

    private TransactionResponse toResponse(Transaction txn) {
        return TransactionResponse.builder()
                .id(txn.getId())
                .fromAccountId(txn.getFromAccountId())
                .toAccountId(txn.getToAccountId())
                .amount(txn.getAmount())
                .type(txn.getType())
                .status(txn.getStatus())
                .createdAt(txn.getCreatedAt())
                .build();
    }
}
