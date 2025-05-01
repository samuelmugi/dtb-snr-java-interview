package com.smugi.store_of_value_svc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("accounts")
public class Account {
    @Id
    private UUID id;
    private UUID customerId;
    private BigDecimal balance;
    private boolean active;
    @Version
    private Long version;
}
