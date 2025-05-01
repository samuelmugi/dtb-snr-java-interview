package com.smugi.profile_svc;

import com.smugi.profile_svc.dto.LoginRequest;
import com.smugi.profile_svc.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProfileSvcIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void register_thenLogin_shouldSucceed() {
        RegisterRequest register = new RegisterRequest();
        register.setEmail("int@test.com");
        register.setUsername("integrationUser");
        register.setPassword("secret");

        webTestClient.post().uri("/auth/register")
            .bodyValue(register)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.token").exists();

        LoginRequest login = new LoginRequest();
        login.setEmail("int@test.com");
        login.setPassword("secret");

        webTestClient.post().uri("/auth/login")
            .bodyValue(login)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.token").exists();
    }
}
