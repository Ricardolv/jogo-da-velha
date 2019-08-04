package com.richard.api.service;

import com.richard.api.model.Game;
import com.richard.api.model.Position;
import com.richard.api.repositories.GameRepository;
import com.richard.api.resources.dto.GameDTO;
import com.richard.api.resources.dto.PositionDTO;
import com.richard.api.services.GameService;
import com.richard.api.services.exception.NotFoundGameException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private GameRepository gameRepository;
    private GameService gameService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        this.gameRepository = mock(GameRepository.class);
        this.gameService = new GameService(this.gameRepository);
    }

    @Test
    public void testStart() {
        Game newGame = Game.builder()
            .id("1")
            .nextPlayer("X")
            .build();

        Mockito.when(this.gameRepository.saveAndFlush(any(Game.class))).thenReturn(newGame);

        GameDTO dto = this.gameService.start();
        assertEquals(newGame.getId(), dto.getId());
    }

    @Test
    public void testMovement_success() {
        Position position = Position.builder()
            .x(0)
            .y(1)
            .player("X")
            .build();
        Set setPosition = new HashSet<>();
        setPosition.add(position);
        Game newGame = Game.builder()
            .id("1")
            .nextPlayer("X")
            .positions(setPosition)
            .build();

        Optional<Game> gameOptional = Optional.of(newGame);
        Mockito.when(this.gameRepository.findById(newGame.getId())).thenReturn(gameOptional);

        PositionDTO positionDTO = PositionDTO.builder()
            .x(1)
            .y(0)
            .build();
        GameDTO movementDTO = GameDTO.builder()
            .id(gameOptional.get().getId())
            .player(gameOptional.get().getNextPlayer())
            .position(positionDTO)
            .build();

        GameDTO result = this.gameService.movement(movementDTO.getId(), movementDTO);

        assertNotNull(result);
    }

    @Test(expected = NotFoundGameException.class)
    public void testMovement_withoutEnteringId_errorNotFoundGameException() {
        String id = new Random().nextBoolean() ? "" : null;
        this.gameService.movement(id , null);

        // expected
        // NotFoundGameException
    }

    @Test(expected = NotFoundGameException.class)
    public void testMovement_nonexistentId_errorNotFoundGameException() {
        String id = String.valueOf(new Random().nextInt());
        this.gameService.movement(id , null);

        // expected
        // NotFoundGameException
    }

}
