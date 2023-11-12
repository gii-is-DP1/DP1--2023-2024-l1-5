package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Games", description = "The Game management API")
@SecurityRequirement(name = "bearerAuth")
public class GameController {
    private final GameService gameService;

     @Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Game>>getAllGames(){
        return  new ResponseEntity<>(gameService.getAllGames(), HttpStatus.OK);
    }

    
}
