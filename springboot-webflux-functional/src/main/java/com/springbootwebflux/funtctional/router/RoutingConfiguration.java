package com.springbootwebflux.funtctional.router;

import com.springbootwebflux.funtctional.handler.UserHandler;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.RouterFunctions.;
/**
 * @author WXY
 */
@Configurable
public class RoutingConfiguration {


    public RouterFunction<ServerRequest> monoRouterFunction(UserHandler userHandler){

    }

}
