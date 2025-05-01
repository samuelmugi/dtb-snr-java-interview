package com.smugi.payment_svc.router;

import com.smugi.payment_svc.dto.TransactionRequest;
import com.smugi.payment_svc.dto.TransactionResponse;
import com.smugi.payment_svc.handler.PaymentHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PaymentRouter {

    @RouterOperations({
            @RouterOperation(
                    path = "/transactions",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = PaymentHandler.class,
                    beanMethod = "process",
                    operation = @Operation(
                            operationId = "processTransaction",
                            summary = "Process a new transaction",
                            tags = {"Payment"},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful transaction",
                                            content = @Content(schema = @Schema(implementation = TransactionResponse.class))
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    content = @Content(schema = @Schema(implementation = TransactionRequest.class))
                            )
                    )
            )
    })
    @Bean
    public RouterFunction<?> routes(PaymentHandler handler) {
        return route(POST("/transactions"), handler::process);
    }
}
