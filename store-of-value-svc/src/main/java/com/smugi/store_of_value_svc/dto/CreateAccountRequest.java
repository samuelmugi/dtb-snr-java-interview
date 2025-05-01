package com.smugi.store_of_value_svc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateAccountRequest {
    private UUID customerId;
    private BigDecimal initialBalance;
}
