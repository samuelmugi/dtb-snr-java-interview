package com.smugi.store_of_value_svc.service;

import com.smugi.store_of_value_svc.dto.AccountResponse;
import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import com.smugi.store_of_value_svc.model.Account;
import com.smugi.store_of_value_svc.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Mono<AccountResponse> create(CreateAccountRequest request) {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .customerId(request.getCustomerId())
                .balance(request.getInitialBalance())
                .active(true)
                .build();
        return repository.save(account).map(this::toResponse);
    }

    public Mono<AccountResponse> getById(UUID id) {
        return repository.findById(id).map(this::toResponse);
    }

    public Flux<AccountResponse> getByCustomerId(UUID customerId) {
        return repository.findByCustomerId(customerId).map(this::toResponse);
    }

    public Mono<AccountResponse> activate(UUID id) {
        return repository.findById(id)
                .flatMap(acc -> {
                    acc.setActive(true);
                    return repository.save(acc);
                })
                .map(this::toResponse);
    }

    public Mono<AccountResponse> deactivate(UUID id) {
        return repository.findById(id)
                .flatMap(acc -> {
                    acc.setActive(false);
                    return repository.save(acc);
                })
                .map(this::toResponse);
    }

    public Mono<AccountResponse> adjustBalance(UUID id, BigDecimal amount) {
        return repository.findById(id)
                .flatMap(acc -> {
                    acc.setBalance(acc.getBalance().add(amount));
                    return repository.save(acc);
                })
                .map(this::toResponse);
    }

    private AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .active(account.isActive())
                .build();
    }
}