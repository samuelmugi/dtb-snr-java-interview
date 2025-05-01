package com.smugi.payment_svc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class Transaction {
    @Id
    private UUID id;
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private String type;
    private String status;
    private LocalDateTime createdAt;
}
