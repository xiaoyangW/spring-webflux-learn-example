package com.springbootwebflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WXY
 */
@RestController
@RequestMapping("/api/user")
public class WebFluxController {

    private Map<Long,User> map = new HashMap<Long,User>();
    @PostConstruct
    public void init(){
        map.put(1L,new User(1,"admin","admin"));
        map.put(2L,new User(1,"admin2","admin2"));
        map.put(3L,new User(1,"admin3","admin3"));
    }
    @GetMapping("/getall")
    public Flux<User> getAllUser(){
        return Flux.fromIterable(map.entrySet().stream().map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }


}
