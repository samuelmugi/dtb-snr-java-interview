package com.smugi.store_of_value_svc.service;

import com.smugi.store_of_value_svc.model.Account;
import com.smugi.store_of_value_svc.repository.AccountRepository;
import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    void create_shouldSaveAndReturnResponse() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setCustomerId(UUID.randomUUID());
        request.setInitialBalance(BigDecimal.valueOf(500));

        when(repository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.create(request))
            .expectNextMatches(resp -> resp.getBalance().compareTo(BigDecimal.valueOf(500)) == 0)
            .verifyComplete();
    }

    @Test
    void getById_shouldReturnAccount() {
        UUID id = UUID.randomUUID();
        Account acc = Account.builder().id(id).balance(BigDecimal.TEN).active(true).build();
        when(repository.findById(id)).thenReturn(Mono.just(acc));

        StepVerifier.create(service.getById(id))
            .expectNextMatches(resp -> resp.getId().equals(id))
            .verifyComplete();
    }

    @Test
    void getByCustomerId_shouldReturnAccounts() {
        UUID customerId = UUID.randomUUID();
        Account acc = Account.builder().id(UUID.randomUUID()).customerId(customerId).balance(BigDecimal.ONE).active(true).build();
        when(repository.findByCustomerId(customerId)).thenReturn(Flux.fromIterable(List.of(acc)));

        StepVerifier.create(service.getByCustomerId(customerId))
            .expectNextCount(1)
            .verifyComplete();
    }

    @Test
    void activate_shouldSetActiveTrue() {
        UUID id = UUID.randomUUID();
        Account acc = Account.builder().id(id).active(false).build();
        when(repository.findById(id)).thenReturn(Mono.just(acc));
        when(repository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.activate(id))
            .expectNextMatches(resp -> resp.isActive())
            .verifyComplete();
    }

    @Test
    void deactivate_shouldSetActiveFalse() {
        UUID id = UUID.randomUUID();
        Account acc = Account.builder().id(id).active(true).build();
        when(repository.findById(id)).thenReturn(Mono.just(acc));
        when(repository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.deactivate(id))
            .expectNextMatches(resp -> !resp.isActive())
            .verifyComplete();
    }

    @Test
    void adjustBalance_shouldAddToBalance() {
        UUID id = UUID.randomUUID();
        Account acc = Account.builder().id(id).balance(BigDecimal.valueOf(100)).build();
        when(repository.findById(id)).thenReturn(Mono.just(acc));
        when(repository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.adjustBalance(id, BigDecimal.valueOf(50)))
            .expectNextMatches(resp -> resp.getBalance().compareTo(BigDecimal.valueOf(150)) == 0)
            .verifyComplete();
    }
}
