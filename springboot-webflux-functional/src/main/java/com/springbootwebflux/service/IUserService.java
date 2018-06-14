package com.springbootwebflux.service;

import com.springbootwebflux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author WXY
 */
public interface IUserService {

    Flux<User> getAllUser();
    Mono<User> getUserById(Long id);
    Mono<Void> saveUser(Mono<User> userMono);

}
