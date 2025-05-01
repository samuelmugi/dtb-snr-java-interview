package com.smugi.store_of_value_svc.service;

import com.smugi.store_of_value_svc.dto.AccountResponse;
import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import com.smugi.store_of_value_svc.model.Account;
import com.smugi.store_of_value_svc.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveAndReturnAccountResponse() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setCustomerId(UUID.randomUUID());
        request.setInitialBalance(BigDecimal.valueOf(500));

        Account account = Account.builder()
            .id(UUID.randomUUID())
            .customerId(request.getCustomerId())
            .balance(request.getInitialBalance())
            .active(true)
            .build();

        when(repository.save(any(Account.class))).thenReturn(Mono.just(account));

        StepVerifier.create(service.create(request))
            .expectNextMatches(resp ->
                resp.getCustomerId().equals(request.getCustomerId()) &&
                resp.getBalance().compareTo(request.getInitialBalance()) == 0
            )
            .verifyComplete();
    }
}
