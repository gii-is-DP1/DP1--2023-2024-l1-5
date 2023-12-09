package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
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
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;
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
    private final RoundService roundService;
    private static final String PLAYER_AUTH = "PLAYER";
    private static final String QUICK_PLAY = "QUICK_PLAY";
    private static final String COMPETITIVE = "COMPETITIVE";

    @Autowired
    public GameController(GameService gameService, UserService userService, PlayerService playerService,
            RoundService roundService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.userService = userService;
        this.roundService = roundService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<Game> games = gameService.getAllGames(); // Obtener la lista de objetos Game
        List<GameDTO> gameDTOs = games.stream()
                .map(game -> new GameDTO(game)) // Convertir Game a GameDTO
                .collect(Collectors.toList());

        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }

    @GetMapping("/waiting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameDTO>> getWaitingGames() {
        List<Game> games = gameService.getWaitingGames(); 
        List<GameDTO> gameDTOs = games.stream()
                .map(game -> new GameDTO(game))
                .collect(Collectors.toList());

        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }

    @GetMapping("/inProgress")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameDTO>> getInProgressGames() {
        List<Game> games = gameService.getInProgressGames(); 
        List<GameDTO> gameDTOs = games.stream()
                .map(game -> new GameDTO(game))
                .collect(Collectors.toList());

        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }

    @GetMapping("/finalized")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameDTO>> getFinalizedGames() {
        List<Game> games = gameService.getFinalizedGames(); 
        List<GameDTO> gameDTOs = games.stream()
                .map(game -> new GameDTO(game)) 
                .collect(Collectors.toList());

        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }
    


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") Integer id) {
        Optional<Game> g = gameService.getGameById(id);
        if (!g.isPresent())
            throw new ResourceNotFoundException("Game", "id", id);

        GameDTO gameDTO = new GameDTO(g.get());

        return new ResponseEntity<>(gameDTO, HttpStatus.OK);
    }

    @GetMapping("/quick/joinRandom")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getRandomQuickGame() {
        Optional<Game> g = gameService.getRandomGame(QUICK_PLAY);
        if (!g.isPresent()) {
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
        }
        return new ResponseEntity<>(g.get(), HttpStatus.OK);
    }

    @GetMapping("/competitive/joinRandom")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getRandomCompGame() {
        Optional<Game> g = gameService.getRandomGame(COMPETITIVE);
        if (!g.isPresent()) {
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
        }
        return new ResponseEntity<>(g.get(), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partida creada exitosamente"),
            // Añado un nuevo código de estado sin documentar en swagger
            @ApiResponse(responseCode = "409", description = "El jugador ya tiene una partida activa", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    
    @PostMapping()
    public ResponseEntity<Game> createGame(@Valid @RequestBody GameRequest gameRequest) throws DataAccessException {
        // POR AHORA NO SE TIENE EN CUENTA SI ES COMPETITIVO O NO

        User user = userService.findCurrentUser();
        Game newGame = new Game();
        newGame.setId(null);
        Game savedGame;
        BeanUtils.copyProperties(gameRequest, newGame, "id");

        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
            Player player = playerService.findPlayerByUser(user);
            // Establecer los valores predeterminados para los atributos
            List<Player> players = new ArrayList<>();
            newGame.setGameMode(gameRequest.getGameMode());
            newGame.setCreator(player);
            newGame.setStatus(GameStatus.WAITING);
            newGame.setNumPlayers(0);
            newGame.setGameTime(0);
            players.add(player);
            newGame.setPlayers(players);
            newGame.setNumPlayers(players.size());
            savedGame = this.gameService.saveGame(newGame, player);

        } else {
            throw new ResourceNotFoundException("User", "id", user.getId());
        }

        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    @PutMapping("/quick/joinRandom")
    public ResponseEntity<Game> joinQuickGame(@RequestBody @Valid int id) {
        User user = userService.findCurrentUser();
        Game aux = gameService.getRandomGame("QUICK_PLAY").get();
        int gameId = aux.getId();
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
            Game savedGame = this.gameService.updateGame(id, gameId);
            return new ResponseEntity<>(savedGame, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> updateGame(@PathVariable("id") Integer id,
            @Valid @RequestBody GameRequestPUT gameRequest) {
        Optional<Game> g = gameService.getGameById(id);
        User user = userService.findCurrentUser();
        Player p1 = playerService.findPlayerByUser(user);
        if (!g.isPresent())
            throw new ResourceNotFoundException("Game", "id", id);

        Game game = g.get();

        BeanUtils.copyProperties(gameRequest, game, "id", "rounds", "players");
        // BeanUtils.copyProperties(gameRequest, game, "id", "status", "rounds",
        // "players" );
        // game.setStatus(getGameStatusFromString(gameRequest.getStatus(),game));

        List<Round> lsRounds = new ArrayList<Round>();
        for (Integer roundId : gameRequest.getRounds()) {
            if (!(roundId == 0)) {
                Optional<Round> r = roundService.getRoundById(roundId);
                if (!r.isPresent())
                    throw new ResourceNotFoundException("Round", "id", roundId);
                lsRounds.add(r.get());
            }
        }
        List<Round> rounds = game.getRounds();
        rounds.addAll(lsRounds);
        game.setRounds(rounds);

        List<Player> lsPlayer = new ArrayList<Player>();
        for (Integer playerId : gameRequest.getPlayers()) {
            if (!(playerId == 0)) {
                Optional<Player> p = playerService.getPlayerById(playerId);
                if (!p.isPresent())
                    throw new ResourceNotFoundException("Player", "id", playerId);
                lsPlayer.add(p.get());
            }
        }
        List<Player> players = game.getPlayers();
        players.addAll(lsPlayer);
        game.setPlayers(players);

        game.setNumPlayers(game.getPlayers().size());
        Game savedGame = this.gameService.saveGame(game, p1);

        return new ResponseEntity<>(savedGame, HttpStatus.OK);
    }

}
