package org.springframework.samples.petclinic.game;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.http.HttpStatus;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Games", description = "The Game management API")
@SecurityRequirement(name = "bearerAuth")
public class GameController {
    private final GameService gameService;
    private final UserService userService;
    private final PlayerService playerService;
    private static final String PLAYER_AUTH = "PLAYER";
    
    @Autowired
	public GameController(GameService gameService, UserService userService, PlayerService playerService) {
		this.gameService = gameService;
		this.playerService = playerService;
        this.userService = userService;
	}
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Game>>getAllGames(){
        return  new ResponseEntity<>(gameService.getAllGames(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getGameById(@PathVariable("id")Integer id){
        Optional<Game> g=gameService.getGameById(id);
        if(!g.isPresent())
            throw new ResourceNotFoundException("Game", "id", id);
        return new ResponseEntity<>(g.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Game> createGame(@Valid @RequestBody GameRequest gameRequest) throws DataAccessException{ 
        // IMPORTANTE: Se debe comprobar antes que el usuario no tiene una partida en curso ni en espera creada
        User user = userService.findCurrentUser();
        Game newGame = new Game();
        
        Game savedGame;
        BeanUtils.copyProperties(gameRequest, newGame, "id");

        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            Player player = playerService.findPlayerByUser(user);
            // Establecer los valores predeterminados para los atributos
            newGame.setGameMode(gameRequest.getGameMode());
            newGame.setCreator(player.getId());
            newGame.setGameStatus(GameStatus.WAITING);
            newGame.setNumPlayers(0);
            newGame.setGameTime(0);
            savedGame = this.gameService.saveGame(newGame);
        } else {
			savedGame = this.gameService.saveGame(newGame);
		}
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }
}
