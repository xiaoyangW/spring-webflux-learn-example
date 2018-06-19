package com.springbootwebflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@WebFluxTest
public class SpringbootWebfluxApplicationTests {

	@Autowired
	private  WebTestClient webClient;

	@Test
	public void contextLoads() {
		User user = new User(4L,"admin4","admin4");
		webClient.post().uri("/api/user/save")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(user))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(String.class)
				.isEqualTo("添加成功");
	}

}
