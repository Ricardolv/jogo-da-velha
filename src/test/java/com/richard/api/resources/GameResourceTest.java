package com.richard.api.resources;

import com.richard.api.repositories.GameRepository;
import com.richard.api.services.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
public class GameResourceTest {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    /*
     * POST /game
     */
    @Test
    public void testStart() {
        Map responseBody = this.testClient.post()
            .uri("/game")
            .exchange().expectStatus().isEqualTo(200)
            .expectBody(Map.class).returnResult().getResponseBody();

        assertNotNull("No response id generated", responseBody.get("id"));
        assertNotNull("No response firstPlayer generated", responseBody.get("firstPlayer"));
    }

    @Test
    public void testMovement_withoutEnteringId_errorBAD_REQUEST() {
        this.testClient.post()
            .uri("/game/"+ null +"/movement")
            .exchange().expectStatus().isEqualTo(400);
    }

    @Test
    public void testMovement_nonexistentId_errorBAD_REQUEST() {
        this.testClient.post()
            .uri("/game/"+ new Random().nextInt() +"/movement")
            .exchange().expectStatus().isEqualTo(400);
    }




}
