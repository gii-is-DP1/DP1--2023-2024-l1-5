package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.exceptions.WaitingGamesNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    private final GameInfoService gameInfoService;
    private static final String PLAYER_AUTH = "PLAYER";
    private static final String QUICK_PLAY = "QUICK_PLAY";
    private static final String COMPETITIVE = "COMPETITIVE";

    @Autowired
    public GameController(GameService gameService, UserService userService, PlayerService playerService,
            RoundService roundService, GameInfoService gameInfoService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.userService = userService;
        this.roundService = roundService;
        this.gameInfoService = gameInfoService;
    }

    @InitBinder("game")
    public void initGameBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new GameValidator());
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

    @GetMapping("/playerId/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Game>> getGamesByPlayerId(@PathVariable("playerId") Integer playerId) {
        List<Game> games = gameService.getGamesByPlayerId(playerId);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/numGames/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getNumGamesByPlayerId(@PathVariable("playerId") Integer playerId) {
        Integer numGames = gameService.getNumGamesByPlayerId(playerId);
        return new ResponseEntity<>(numGames, HttpStatus.OK);
    }

    @GetMapping("/numGamesWin/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getNumGamesWinByPlayerId(@PathVariable("playerId") Integer playerId) {
        Integer numGames = gameService.getNumGamesWinByPlayerId(playerId);
        return new ResponseEntity<>(numGames, HttpStatus.OK);
    }

    @GetMapping("/timeGames/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getTimeGamesByPlayerId(@PathVariable("playerId") Integer playerId) {
        Integer numGames = gameService.getTimesGamesWinByPlayerId(playerId);
        return new ResponseEntity<>(numGames, HttpStatus.OK);
    }

    @GetMapping("/maxTimeGames/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getMaxTimeGamesByPlayerId(@PathVariable("playerId") Integer playerId) {
        Integer numGames = gameService.getMaxTimeGamesByPlayerId(playerId);
        return new ResponseEntity<>(numGames, HttpStatus.OK);
    }

    @GetMapping("/minTimeGames/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getMinTimeGamesByPlayerId(@PathVariable("playerId") Integer playerId) {
        Integer numGames = gameService.getMinTimeGamesByPlayerId(playerId);
        return new ResponseEntity<>(numGames, HttpStatus.OK);
    }

    @GetMapping("/avgTimeGames/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getAvgTimeGamesByPlayerId(@PathVariable("playerId") Integer playerId) {
        Double numGames = gameService.getAvgTimeGamesByPlayerId(playerId);
        return new ResponseEntity<>(numGames, HttpStatus.OK);
    }

    @GetMapping("/numGamesMode/{playerId}/{gameMode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getNumGamesModeByPlayerId(@PathVariable("playerId") Integer playerId,
            @PathVariable("gameMode") String gameMode) {
        List<Game> games = gameService.getGamesByPlayerId(playerId);
        Integer result = 0;
        List<Round> rounds = games.stream().map(game -> game.getRounds()).flatMap(List::stream)
                .collect(Collectors.toList());

        for (Round round : rounds) {
            if (round.getRoundMode().toString().equals(gameMode)) 
                result++; 
        }
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RankingDTO>> getRanking() {
        Map<String, Integer> ranking = gameService.getRanking();
        List<RankingDTO> rankingDTO = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
            rankingDTO.add(new RankingDTO(entry.getKey(), entry.getValue()));
        }
        rankingDTO.sort(Comparator.comparing(RankingDTO::getNumGames).reversed());
        return new ResponseEntity<>(rankingDTO, HttpStatus.OK);
    }

    @GetMapping("/ranking/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getRankingPlayerId(@PathVariable("playerId") Integer playerId) {
        Integer result = gameService.myRank(playerId);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    @GetMapping("/myGame/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Game>> getPlayerGames(@PathVariable("userId") Integer userId) {
        Player player = playerService.getPlayerByUserId(userId);
        List<Game> games = gameService.getGameFromPlayer(player);

        return new ResponseEntity<>(games, HttpStatus.OK);
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
        GameInfo gameInfo = new GameInfo();
        BeanUtils.copyProperties(gameRequest, newGame, "id");

        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
            Player player = playerService.findPlayerByUser(user);
            // Establecer los valores predeterminados para los atributos
            List<Player> players = new ArrayList<>();
            GameMode gameMode = gameRequest.getGameMode();
            newGame.setGameMode(gameMode);
            newGame.setCreator(player);
            newGame.setStatus(GameStatus.WAITING);
            newGame.setGameTime(0);
            players.add(player);
            newGame.setPlayers(players);
            newGame.setNumPlayers(players.size());
            savedGame = gameService.saveGame(newGame, player);
            gameInfo.setGameMode(gameMode);
            gameInfo.setNumPlayers(players.size());
            gameInfo.setCreator(player);
            gameInfo.setStatus(GameStatus.WAITING);
            gameInfo.setGame(savedGame);
            gameInfoService.saveGameInfo(gameInfo);
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
            Game savedGame = gameService.updateGame(id, gameId);
            gameInfoService.updateGameInfo(gameId);
            return new ResponseEntity<>(savedGame, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/winner/{game_id}/{player_id}")
    public ResponseEntity<Game> updateWinner(@PathVariable("game_id") Integer gameId, @PathVariable("player_id") Integer playerId, @RequestParam("time") Integer time) {
        Game toUpdate = this.gameService.getGameById(gameId).get();
        toUpdate.setWinner(playerId);
        toUpdate.setGameTime(time);
        gameService.save(toUpdate);
        return new ResponseEntity<>(toUpdate, HttpStatus.OK);
    }

    @GetMapping("/winner/{game_id}")
    public ResponseEntity<Integer> getWinner(@PathVariable("game_id") Integer gameId) {
        Game game = this.gameService.getGameById(gameId).orElseThrow(() -> new ResourceNotFoundException("Game", "id", gameId));
        Integer res = game.getWinner();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    

    @PutMapping("/quick/joinInvitation/{game_id}")
    public ResponseEntity<Game> joinQuickGameById(@PathVariable("game_id") Integer game_id) {
        User user = userService.findCurrentUser();
        Player player = playerService.getPlayerByUserId(user.getId());
        Game game = gameService.getGameById(game_id).get();
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true) && game.getNumPlayers()<8) {
            Game savedGame = this.gameService.updateGame(player.getId(), game_id);
            gameInfoService.updateGameInfo(game_id);
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

    @DeleteMapping("/{gameId}")
    public ResponseEntity<MessageResponse> deleteGame(@PathVariable("gameId") Integer gameId) {
        gameService.deleteGame(gameId);
        return new ResponseEntity<>(new MessageResponse("Game Deleted!"), HttpStatus.OK);
    }

    @DeleteMapping("/{gameId}/players/{currentUserId}")
    public ResponseEntity<MessageResponse> deletePlayerFromGame(@PathVariable("gameId") Integer gameId,@PathVariable("currentUserId") Integer currentUserId) {
        gameService.deletePlayerFromGame(gameId, currentUserId);
        return new ResponseEntity<>(new MessageResponse("Player deleted from the Game!"), HttpStatus.OK);
    }

    @PutMapping("/updateInprogress/{gameId}")
    public ResponseEntity<Game> updateGameInProgress(@PathVariable("gameId") Integer gameId) {
        Game gameToUpdate = gameService.getGameById(gameId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Game", "id", gameId));
        gameToUpdate.setStatus(GameStatus.IN_PROGRESS);
        Game savedGame = gameService.save(gameToUpdate);
        return new ResponseEntity<>(savedGame, HttpStatus.OK);
    }
    @PutMapping("/updateFinalized/{gameId}")
    public ResponseEntity<Game> updateGameFinalized(@PathVariable("gameId") Integer gameId) {
        Game gameToUpdate = gameService.getGameById(gameId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Game", "id", gameId));
        gameToUpdate.setStatus(GameStatus.FINALIZED);
        Game savedGame = gameService.save(gameToUpdate);
        return new ResponseEntity<>(savedGame, HttpStatus.OK);
    }

    @GetMapping("/inProgress/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getInProgressPlayerGame(@PathVariable("playerId") Integer playerId){
        Game game = gameService.getInProgressGame(playerId);
        if(game == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

}
