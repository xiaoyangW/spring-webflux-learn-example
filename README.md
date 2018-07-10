### SpringWebflux示例


**简介**

 > Spring WebFlux是Spring Framework 5.0中引入的新的反应式Web框架。
 与Spring MVC不同，它不需要Servlet API，完全异步和非阻塞，
 并通过Reactor项目实现Reactive Streams规范。
 并且可以在诸如Netty，Undertow和Servlet 3.1+容器的服务器上运行。

**Reactor中的Mono和Flux**

>Flux 和 Mono 是 Reactor 中的两个基本概念。Flux 表示的是包含 0 到 N 个元素的异步序列。
在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。
当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。Mono 表示的是包含 0 或者 1 个元素的异步序列。
该序列中同样可以包含与 Flux 相同的三种类型的消息通知。Flux 和 Mono 之间可以进行转换。
对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象。把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。 [了解更多](https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html)

**WebFlux的使用方式**

![图片简介](https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/images/spring-mvc-and-webflux-venn.png)
>如图所示，WebFlux支持两种编程方式

   - 基于SpringMvc注解@Controller
   - 基于Java8 lambda样式路由和处理

使用WebFlux需要单独引用它的依赖,我使用的springboot,依赖如下：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<!--reactor的测试依赖-->
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
```



**示例**

- 基于SpringMvc注解

  > 与使用SpringMvc不同的是使用SpringWebFlux同一使用Mono<>,Flux<>对象同意返回数据，如下

  ```java
  @RestController
  @RequestMapping("/api/user")
  public class WebFluxController {
  
      private Map<Long,User> map = new HashMap<Long,User>(10);
      @PostConstruct
      public void init(){
          map.put(1L,new User(1,"admin","admin"));
          map.put(2L,new User(1,"admin2","admin2"));
          map.put(3L,new User(1,"admin3","admin3"));
      }
      @GetMapping("/getAll")
      public Flux<User> getAllUser(){
          return Flux.fromIterable(map.entrySet().stream().map(Map.Entry::getValue)
                  .collect(Collectors.toList()));
      }
      @GetMapping("/{id}")
      public Mono<User> getUserById(@PathVariable("id") Long id){
          return Mono.just(map.get(id));
      }
      @PostMapping("/save")
      public Mono<ResponseEntity<String>> save(@RequestBody User user){
          map.put(user.getUid(),user);
          return Mono.just(new ResponseEntity<>("添加成功", HttpStatus.CREATED));
      }
  }
  ```

  具体实现代码可查看[springboot-webflux](https://github.com/xiaoyangW/spring-webflux-learn-example/tree/master/springboot-webflux) 

- 基于功能

  > 处理请求的类，实现具体的业务逻辑，接口 ServerRequest 表示的是一个 HTTP 请求体。通过ServerRequest 对象可获取到请求的相关信息，如请求路径、查询参数和请求内容等。方法 的返回值是一个 Mono<T extends ServerResponse>对象。接口 ServerResponse 用来表示 HTTP 响应。ServerResponse 中包含了很多静态方法来创建不同 HTTP 状态码的响应对象 

  ```java
  @Component
  public class UserHandler {
  
      private IUserService userService;
      @Autowired
      public UserHandler(IUserService userService) {
          this.userService = userService;
      }
  
      public Mono<ServerResponse> getAllUser(ServerRequest serverRequest){
          Flux<User> allUser = userService.getAllUser();
          return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(allUser,User.class);
      }
  
      public Mono<ServerResponse> getUserById(ServerRequest serverRequest){
          //获取url上的id
          Long uid = Long.valueOf(serverRequest.pathVariable("id"));
          Mono<User> user = userService.getUserById(uid);
          return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user,User.class);
      }
  
      public Mono<ServerResponse> saveUser(ServerRequest serverRequest){
          Mono<User> user = serverRequest.bodyToMono(User.class);
          return ServerResponse.ok().build(userService.saveUser(user));
      }
  
  }
  ```

  > 为Handler类添加路由信息，

  ```java
  
  @Configuration
  public class RoutingConfiguration {
  
      @Bean
      public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler){
          return route(GET("/api/user").and(accept(MediaType.APPLICATION_JSON)),userHandler::getAllUser)
                  .andRoute(GET("/api/user/{id}").and(accept(MediaType.APPLICATION_JSON)),userHandler::getUserById)
                  .andRoute(POST("/api/save").and(accept(MediaType.APPLICATION_JSON)),userHandler::saveUser);
      }
  
  }
  ```
  
  具体实现代码可查看[springboot-webflux-functional](https://github.com/xiaoyangW/spring-webflux-learn-example/tree/master/springboot-webflux-functional) 
  [博客](http://www.wxiaoyang.top/2018/06/16/webflux/)