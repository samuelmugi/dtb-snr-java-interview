package com.smugi.payment_svc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequest {
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private String type; // TOPUP, WITHDRAWAL, TRANSFER
}
