package org.springframework.samples.petclinic.game;


import java.util.List;
import java.util.Optional;

import javax.management.monitor.GaugeMonitor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.exceptions.ActiveGameException;
import org.springframework.samples.petclinic.game.exceptions.WaitingGamesNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private static final String QUICK_START = "QUICK_START";
    private static final String COMPETITIVE = "COMPETITIVE";
    
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

    @GetMapping("/quick/joinRandom")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getRandomQuickGame(){
        Optional<Game> g=gameService.getRandomGame(QUICK_START);
        if(!g.isPresent()){
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
        }
        return new ResponseEntity<>(g.get(), HttpStatus.OK);
    }
    @GetMapping("/competitive/joinRandom")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getRandomCompGame(){
        Optional<Game> g=gameService.getRandomGame(COMPETITIVE);
        if(!g.isPresent()){
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
        }
        return new ResponseEntity<>(g.get(), HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Partida creada exitosamente"),
        // Añado un nuevo código de estado sin documentar en swagger
        @ApiResponse(responseCode = "409", description ="El jugador ya tiene una partida activa",content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @PostMapping()
    public ResponseEntity<Game> createGame(@Valid @RequestBody GameRequest gameRequest) throws DataAccessException{
        //POR AHORA NO SE TIENE EN CUENTA SI ES COMPETITIVO O NO

        User user = userService.findCurrentUser();
        Game newGame = new Game();
        Game savedGame;
        BeanUtils.copyProperties(gameRequest, newGame, "id");

        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            Player player = playerService.findPlayerByUser(user);
            boolean hasActiveGame = gameService.hasActiveGame(player);
            if (hasActiveGame) {
                throw new ActiveGameException("El jugador ya tiene una partida activa");
            }else{
                // Establecer los valores predeterminados para los atributos
                List<Player> players = new ArrayList<>();
                newGame.setGameMode(gameRequest.getGameMode());
                newGame.setCreator(player);
                newGame.setGameStatus(GameStatus.WAITING);
                newGame.setGameTime(0);
                players.add(player);
                newGame.setPlayers(players);
                newGame.setNumPlayers(players.size());
                savedGame = this.gameService.saveGame(newGame);
            }
        } else {
			savedGame = this.gameService.saveGame(newGame);
            
		}
        
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    @PutMapping("/quick/joinRandom")
    public ResponseEntity<Game> joinGame(@RequestBody @Valid int id,@RequestBody @Valid GameRequest gameRequest){
        User user = userService.findCurrentUser();
        Game aux = gameService.getRandomGame(COMPETITIVE).get();
        int gameId=aux.getId();
        if(user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            Game savedGame=this.gameService.updateGame(id,gameId);
            return new ResponseEntity<>(savedGame, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    
}
