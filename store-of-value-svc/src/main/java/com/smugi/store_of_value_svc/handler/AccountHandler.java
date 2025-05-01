package com.smugi.store_of_value_svc.handler;

import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import com.smugi.store_of_value_svc.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountHandler {

    private final AccountService service;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateAccountRequest.class)
                .flatMap(service::create)
                .flatMap(res -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return service.getById(id)
                .flatMap(res -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res));
    }

    public Mono<ServerResponse> getByCustomerId(ServerRequest request) {
        UUID customerId = UUID.fromString(request.pathVariable("customerId"));
        return service.getByCustomerId(customerId)
                .collectList()
                .flatMap(res -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res));
    }

    public Mono<ServerResponse> deactivate(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return service.deactivate(id)
                .flatMap(res -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res));
    }
}
