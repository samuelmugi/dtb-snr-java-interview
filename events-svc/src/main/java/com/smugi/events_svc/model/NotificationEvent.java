package com.smugi.events_svc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor // ✅ Required for Jackson deserialization
@AllArgsConstructor
public class NotificationEvent {
    private UUID transactionId;
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private String type;
    private LocalDateTime createdAt;
}
