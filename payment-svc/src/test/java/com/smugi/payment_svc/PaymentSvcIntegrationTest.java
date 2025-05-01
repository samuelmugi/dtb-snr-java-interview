package com.smugi.payment_svc;

import com.smugi.payment_svc.dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PaymentSvcIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void processTransaction_shouldSucceed() {
        TransactionRequest request = new TransactionRequest();
        request.setFromAccountId(UUID.randomUUID());
        request.setToAccountId(UUID.randomUUID());
        request.setAmount(BigDecimal.valueOf(2000));
        request.setType("TRANSFER");

        webTestClient.post().uri("/transactions")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.status").isEqualTo("SUCCESS")
            .jsonPath("$.amount").isEqualTo(2000)
            .jsonPath("$.type").isEqualTo("TRANSFER");
    }
}
