package com.smugi.store_of_value_svc.router;

import com.smugi.store_of_value_svc.dto.AccountResponse;
import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import com.smugi.store_of_value_svc.handler.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = AccountHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = "createAccount",
                            summary = "Create a new account",
                            tags = {"Account"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful operation",
                                            content = @Content(schema = @Schema(implementation = AccountResponse.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    content = @Content(schema = @Schema(implementation = CreateAccountRequest.class))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/accounts/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = AccountHandler.class,
                    beanMethod = "getById",
                    operation = @Operation(
                            operationId = "getAccountById",
                            summary = "Get an account by ID",
                            tags = {"Account"},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Account ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful operation",
                                            content = @Content(schema = @Schema(implementation = AccountResponse.class))),
                                    @ApiResponse(responseCode = "404", description = "Account not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/customer/{customerId}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = AccountHandler.class,
                    beanMethod = "getByCustomerId",
                    operation = @Operation(
                            operationId = "getAccountsByCustomerId",
                            summary = "Get accounts by customer ID",
                            tags = {"Account"},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "customerId", description = "Customer ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful operation",
                                            content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/{id}/deactivate",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.PATCH,
                    beanClass = AccountHandler.class,
                    beanMethod = "deactivate",
                    operation = @Operation(
                            operationId = "deactivateAccount",
                            summary = "Deactivate an account",
                            tags = {"Account"},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Account ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful operation",
                                            content = @Content(schema = @Schema(implementation = AccountResponse.class))),
                                    @ApiResponse(responseCode = "404", description = "Account not found")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<?> routes(AccountHandler handler) {
        return route(POST("/accounts"), handler::create)
                .andRoute(GET("/accounts/{id}"), handler::getById)
                .andRoute(GET("/accounts/customer/{customerId}"), handler::getByCustomerId)
                .andRoute(PATCH("/accounts/{id}/deactivate"), handler::deactivate);
    }
}
