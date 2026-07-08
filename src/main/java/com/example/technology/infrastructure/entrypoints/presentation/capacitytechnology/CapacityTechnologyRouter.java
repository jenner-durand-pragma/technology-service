package com.example.technology.infrastructure.entrypoints.presentation.capacitytechnology;

import com.example.technology.infrastructure.entrypoints.presentation.capacitytechnology.handlers.CreateCapacityTechnologyHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CapacityTechnologyRouter {

    @Bean("capacityTechnologyRouterFunction")
    @RouterOperations({
            @RouterOperation(
                    path = "/api/capacities/technologies",
                    beanClass = CreateCapacityTechnologyHandler.class,
                    beanMethod = "handle",
                    method = RequestMethod.POST
            )
    })
    public RouterFunction<ServerResponse> routerFunction(
        CreateCapacityTechnologyHandler createCapacityTechnologyHandler
    ) {
        return RouterFunctions
                .route()
                .path(
                        "/capacities/technologies",
                        builder -> builder
                                .POST("", createCapacityTechnologyHandler::handle)
                                .build()
                )
                .build();
    }
}
