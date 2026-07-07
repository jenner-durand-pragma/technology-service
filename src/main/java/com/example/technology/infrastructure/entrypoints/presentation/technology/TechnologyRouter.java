package com.example.technology.infrastructure.entrypoints.presentation.technology;

import com.example.technology.infrastructure.entrypoints.presentation.technology.handlers.CreateTechnologyHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TechnologyRouter {

    @Bean("technologyRouterFunction")
    @RouterOperations({
            @RouterOperation(
                    path = "/api/technologies",
                    beanClass = CreateTechnologyHandler.class,
                    beanMethod = "handle",
                    method = RequestMethod.POST
            )
    })
    public RouterFunction<ServerResponse> routerFunction(
        CreateTechnologyHandler createTechnologyHandler
    ) {
        return RouterFunctions
                .route()
                .path(
                        "/technologies",
                        builder -> builder
                                .POST("", createTechnologyHandler::handle)
                                .build()
                )
                .build();
    }
}
