package com.richard.api.services;

import com.richard.api.model.Game;
import com.richard.api.model.Position;
import com.richard.api.repositories.GameRepository;
import com.richard.api.resources.dto.GameDTO;
import com.richard.api.services.exception.NotFoundGameException;
import com.richard.api.services.exception.NotPlayerTurnException;
import com.richard.api.services.exception.PositionAlreadyFilledException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDTO start() {
        String firstPlayer = new Random().nextBoolean() ? "X" : "O";
        Game game = Game.builder()
                        .nextPlayer(firstPlayer)
                        .build();
        Game gamePersiste = gameRepository.saveAndFlush(game);
        GameDTO dto = GameDTO.builder().id(gamePersiste.getId()).build();
        dto.setFirstPlayer(firstPlayer);
        return dto;
    }

    public GameDTO movement(String id, GameDTO gameDTO) {
        GameDTO dto = null;

        if (StringUtils.isBlank(id)) {
            throw new NotFoundGameException();
        }

        Optional<Game> gameOptional = gameRepository.findById(id);
        if (!gameOptional.isPresent()) {
            throw new NotFoundGameException();
        }

        Game game = gameOptional.get();
        if (!game.getNextPlayer().equalsIgnoreCase(gameDTO.getPlayer())) {
            throw new NotPlayerTurnException();
        }

        game.getPositions().stream().forEach(p -> {
            if (gameDTO.getPosition().getX() == p.getX()
                && gameDTO.getPosition().getY() == p.getY()) {
                throw new PositionAlreadyFilledException();
            }
        });

        if (game.isNinePlays()) {
            return GameDTO.builder()
                    .status("Partida finalizada")
                    .winner("Draw")
                    .build();
        }

        String player = gameDTO.getPlayer().toUpperCase();
        Position position = Position.builder()
                                .x(gameDTO.getPosition().getX())
                                .y(gameDTO.getPosition().getY())
                                .player(player)
                                .build();
        game.getPositions().add(position);
        game.setNextPlayer(getNextPlayer(player));
        Game gamePersist = gameRepository.save(game);

        if (game.isNinePlays()) {
            return GameDTO.builder()
                .status("Partida finalizada")
                .winner("Draw")
                .build();
        }

        dto = GameDTO.builder().build();
        String[][] matrix = getMatrix(game.getPositions());
        if (checkVictory(matrix, player)) {
            dto.setWinner(player);
            dto.setMsg("Partida finalizada");
        }

        descriptionGame(matrix);
        return dto;
    }


    private boolean checkVictory(String[][] matrix, String signal) {
        if ((matrix[0][0].equalsIgnoreCase(signal)
                && matrix[1][0].equalsIgnoreCase(signal)
                && matrix[2][0].equalsIgnoreCase(signal)) ||  //line

            (matrix[0][1].equalsIgnoreCase(signal)
                && matrix[1][1].equalsIgnoreCase(signal)
                && matrix[2][1].equalsIgnoreCase(signal)) ||  //line

            (matrix[0][2].equalsIgnoreCase(signal)
                && matrix[1][2].equalsIgnoreCase(signal)
                && matrix[2][2].equalsIgnoreCase(signal)) ||  //line

            (matrix[0][0].equalsIgnoreCase(signal)
                && matrix[0][1].equalsIgnoreCase(signal)
                && matrix[0][2].equalsIgnoreCase(signal)) ||  //column

            (matrix[1][0].equalsIgnoreCase(signal)
                && matrix[1][1].equalsIgnoreCase(signal)
                && matrix[2][1].equalsIgnoreCase(signal)) ||  //column

            (matrix[2][0].equalsIgnoreCase(signal)
                && matrix[2][1].equalsIgnoreCase(signal)
                && matrix[2][2].equalsIgnoreCase(signal)) ||  //column

            (matrix[0][0].equalsIgnoreCase(signal)
                && matrix[1][1].equalsIgnoreCase(signal)
                && matrix[2][2].equalsIgnoreCase(signal)) ||  //diagonal

            (matrix[0][2].equalsIgnoreCase(signal)
                && matrix[1][1].equalsIgnoreCase(signal)
                && matrix[2][0].equalsIgnoreCase(signal))) {  //reverse diagonal
            return true;
        }
        return false;
    }

    private String getNextPlayer(String player) {
        return player.equalsIgnoreCase("X") ? "O" : "X";
    }

    private String [][] getMatrix(Set<Position> positions) {
        String [][] matrix = new String [3][3];

        matrix[0][2] = "7";
        matrix[0][1] = "4";
        matrix[0][0] = "1";

        matrix[1][2] = "8";
        matrix[1][1] = "5";
        matrix[1][0] = "2";

        matrix[2][2] = "9";
        matrix[2][1] = "6";
        matrix[2][0] = "3";

        positions.stream().forEach(p -> {
            matrix[p.getX()][p.getY()] = p.getPlayer();
        });

        return matrix;
    }

    private void descriptionGame(String[][] matrix) {
        StringBuilder description = new StringBuilder()
            .append("\n" + matrix[0][2]  + "\t" + "|" + "\t" + matrix[1][2]  + "\t" + "|" + "\t" + matrix[2][2])
            .append("\n" +"----|-------|----")
            .append("\n" + matrix[0][1]  + "\t" + "|" + "\t" + matrix[1][1]  + "\t" + "|" + "\t" + matrix[2][1])
            .append("\n" +"----|-------|----")
            .append("\n" + matrix[0][0]  + "\t" + "|" + "\t" + matrix[1][0]  + "\t" + "|" + "\t" + matrix[2][0]);
        log.info(description.toString());
    }
}
