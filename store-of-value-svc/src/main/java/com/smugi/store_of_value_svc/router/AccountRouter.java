package com.smugi.store_of_value_svc.router;

import com.smugi.store_of_value_svc.dto.AccountResponse;
import com.smugi.store_of_value_svc.dto.CreateAccountRequest;
import com.smugi.store_of_value_svc.dto.AdjustBalanceRequest;
import com.smugi.store_of_value_svc.handler.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
            method = RequestMethod.POST,
            beanClass = AccountHandler.class,
            beanMethod = "create",
            operation = @Operation(
                summary = "Create account",
                requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CreateAccountRequest.class))),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Created", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/accounts/{id}",
            method = RequestMethod.GET,
            beanClass = AccountHandler.class,
            beanMethod = "getById",
            operation = @Operation(
                summary = "Get account by ID",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/accounts/customer/{customerId}",
            method = RequestMethod.GET,
            beanClass = AccountHandler.class,
            beanMethod = "getByCustomerId",
            operation = @Operation(
                summary = "Get accounts by customer ID",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/accounts/{id}/activate",
            method = RequestMethod.PATCH,
            beanClass = AccountHandler.class,
            beanMethod = "activate",
            operation = @Operation(
                summary = "Activate account",
                responses = {
                    @ApiResponse(responseCode = "200", description = "Account activated", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/accounts/{id}/deactivate",
            method = RequestMethod.PATCH,
            beanClass = AccountHandler.class,
            beanMethod = "deactivate",
            operation = @Operation(
                summary = "Deactivate account",
                responses = {
                    @ApiResponse(responseCode = "200", description = "Account deactivated", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/accounts/{id}/adjust-balance",
            method = RequestMethod.PATCH,
            beanClass = AccountHandler.class,
            beanMethod = "adjustBalance",
            operation = @Operation(
                summary = "Adjust account balance",
                requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AdjustBalanceRequest.class))),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Balance adjusted", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
                }
            )
        )
    })
    @Bean
    public RouterFunction<?> accountRoutes(AccountHandler handler) {
        return route(POST("/accounts"), handler::create)
            .andRoute(GET("/accounts/{id}"), handler::getById)
            .andRoute(GET("/accounts/customer/{customerId}"), handler::getByCustomerId)
            .andRoute(PATCH("/accounts/{id}/activate"), handler::activate)
            .andRoute(PATCH("/accounts/{id}/deactivate"), handler::deactivate)
            .andRoute(PATCH("/accounts/{id}/adjust-balance"), handler::adjustBalance);
    }
}
