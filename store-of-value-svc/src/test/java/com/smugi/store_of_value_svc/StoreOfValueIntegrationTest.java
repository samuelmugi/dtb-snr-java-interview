package com.smugi.store_of_value_svc;

import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StoreOfValueIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createAccount_shouldReturnValidAccount() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setCustomerId(UUID.randomUUID());
        request.setInitialBalance(BigDecimal.valueOf(1000));

        webTestClient.post().uri("/accounts")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.balance").isEqualTo(1000)
            .jsonPath("$.active").isEqualTo(true);
    }
}
