package com.springbootwebflux.funtctional.router;

import com.springbootwebflux.funtctional.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
/**
 * @author WXY
 */
@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler){
        return route(GET("/api/user").and(accept(MediaType.APPLICATION_JSON)),userHandler::getAllUser)
                .andRoute(GET("/api/user/{id}").and(accept(MediaType.APPLICATION_JSON)),userHandler::getUserById)
                .andRoute(POST("/api/save").and(accept(MediaType.APPLICATION_JSON)),userHandler::saveUser);
    }

}
