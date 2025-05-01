package com.smugi.payment_svc.repository;

import com.smugi.payment_svc.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {
}
