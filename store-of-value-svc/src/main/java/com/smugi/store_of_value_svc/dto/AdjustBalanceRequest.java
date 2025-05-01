package com.smugi.store_of_value_svc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdjustBalanceRequest {
    private BigDecimal amount;
}
