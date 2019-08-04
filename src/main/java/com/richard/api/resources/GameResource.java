package com.richard.api.resources;

import com.richard.api.resources.dto.GameDTO;
import com.richard.api.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/game")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameResource {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameDTO> start(HttpServletResponse response) {
        GameDTO dto = gameService.start();
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "{id}/movement")
    public ResponseEntity<GameDTO> movement(@PathVariable(value = "id") String id, @RequestBody GameDTO gameDTO) {
        GameDTO game = gameService.movement(id, gameDTO);
        return ResponseEntity.ok(game);
    }



}
