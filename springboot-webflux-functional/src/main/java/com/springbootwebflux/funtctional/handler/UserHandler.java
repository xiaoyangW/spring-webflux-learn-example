package com.springbootwebflux.funtctional.handler;

import com.springbootwebflux.model.User;
import com.springbootwebflux.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author WXY
 */
@Component
public class UserHandler {

    private IUserService userService;
    @Autowired
    public UserHandler(IUserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getAllUser(ServerRequest serverRequest){
        Flux<User> allUser = userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(allUser,User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest){
        //获取url上的id
        Long uid = Long.valueOf(serverRequest.pathVariable("id"));
        Mono<User> user = userService.getUserById(uid);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(user,User.class);
    }
}
