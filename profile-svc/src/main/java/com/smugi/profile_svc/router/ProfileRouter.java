package com.smugi.profile_svc.router;

import com.smugi.profile_svc.dto.AuthResponse;
import com.smugi.profile_svc.dto.LoginRequest;
import com.smugi.profile_svc.dto.RegisterRequest;
import com.smugi.profile_svc.handler.ProfileHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileRouter {
    @RouterOperations({
            @RouterOperation(
                    path = "/auth/register",
                    operation = @Operation(
                            operationId = "register",
                            summary = "Register a new user",
                            tags = {"Authentication"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RegisterRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful registration", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/auth/login",
                    operation = @Operation(
                            operationId = "login",
                            summary = "Login a user",
                            tags = {"Authentication"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LoginRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<?> routes(ProfileHandler handler) {
        return route(POST("/auth/register"), handler::register)
             .andRoute(POST("/auth/login"), handler::login);
    }
}
