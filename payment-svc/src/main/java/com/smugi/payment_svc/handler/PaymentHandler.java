package com.smugi.payment_svc.handler;

import com.smugi.payment_svc.dto.TransactionRequest;
import com.smugi.payment_svc.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentHandler {

    private final PaymentService service;

    public Mono<ServerResponse> process(ServerRequest request) {
        return request.bodyToMono(TransactionRequest.class)
                .flatMap(service::process)
                .flatMap(res -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res));
    }
}
