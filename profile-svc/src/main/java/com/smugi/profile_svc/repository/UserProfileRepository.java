package com.smugi.profile_svc.repository;

import com.smugi.profile_svc.model.UserProfile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, UUID> {
    Mono<UserProfile> findByEmail(String email);
}
