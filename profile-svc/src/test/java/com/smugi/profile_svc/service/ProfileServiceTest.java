package com.smugi.profile_svc.service;

import com.smugi.profile_svc.dto.AuthResponse;
import com.smugi.profile_svc.dto.LoginRequest;
import com.smugi.profile_svc.dto.RegisterRequest;
import com.smugi.profile_svc.model.UserProfile;
import com.smugi.profile_svc.repository.UserProfileRepository;
import com.smugi.profile_svc.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @Mock
    private UserProfileRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ProfileService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldReturnToken() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setUsername("testuser");
        request.setPassword("password");

        when(repository.findByEmail(request.getEmail())).thenReturn(Mono.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded");
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(jwtService.generateToken(any())).thenReturn("mock-token");

        StepVerifier.create(service.register(request))
            .expectNextMatches(resp -> resp.getToken().equals("mock-token"))
            .verifyComplete();
    }

    @Test
    void login_shouldReturnToken() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        UserProfile user = UserProfile.builder()
            .id(UUID.randomUUID())
            .email("test@example.com")
            .username("testuser")
            .password("encoded")
            .role("USER")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        when(repository.findByEmail(request.getEmail())).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("mock-token");

        StepVerifier.create(service.login(request))
            .expectNextMatches(resp -> resp.getToken().equals("mock-token"))
            .verifyComplete();
    }
}
