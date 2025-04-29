package com.smugi.profile_svc.service;

import com.smugi.profile_svc.dto.AuthResponse;
import com.smugi.profile_svc.dto.LoginRequest;
import com.smugi.profile_svc.dto.RegisterRequest;
import com.smugi.profile_svc.model.UserProfile;
import com.smugi.profile_svc.repository.UserProfileRepository;
import com.smugi.profile_svc.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Mono<AuthResponse> register(RegisterRequest request) {
        return repository.findByEmail(request.getEmail())
            .flatMap(existing -> Mono.error(new RuntimeException("Email already in use")))
            .switchIfEmpty(
                repository.save(UserProfile.builder()
                    .id(UUID.randomUUID())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("USER")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build()
                ).map(user -> new AuthResponse(jwtService.generateToken(user)))
            );
    }

    public Mono<AuthResponse> login(LoginRequest request) {
        return repository.findByEmail(request.getEmail())
            .filter(user -> user.isActive() && passwordEncoder.matches(request.getPassword(), user.getPassword()))
            .map(user -> new AuthResponse(jwtService.generateToken(user)))
            .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }
}
