package com.smugi.store_of_value_svc.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class AccountResponse {
    private UUID id;
    private UUID customerId;
    private BigDecimal balance;
    private boolean active;
}
