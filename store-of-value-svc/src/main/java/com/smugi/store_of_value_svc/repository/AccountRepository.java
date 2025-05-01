package com.smugi.store_of_value_svc.repository;

import com.smugi.store_of_value_svc.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {
    Flux<Account> findByCustomerId(UUID customerId);
}
