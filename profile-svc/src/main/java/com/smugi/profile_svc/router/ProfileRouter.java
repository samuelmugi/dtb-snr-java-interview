package com.smugi.profile_svc.router;

import com.smugi.profile_svc.handler.ProfileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileRouter {
    @Bean
    public RouterFunction<?> routes(ProfileHandler handler) {
        return route(POST("/auth/register"), handler::register)
             .andRoute(POST("/auth/login"), handler::login);
    }
}
