package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/v1/gameInfo")
@Tag(name = "GameInfo", description = "The GameInfo management API")
@SecurityRequirement(name = "bearerAuth")
public class GameInfoController {

    private final GameInfoService gameInfoService;
    private final UserService userService;
    private final PlayerService playerService;
    private final RoundService roundService;
    private final GameService gameService;

    public GameInfoController(GameInfoService gameInfoService, UserService userService, PlayerService playerService,
            RoundService roundService, GameService gameService) {
        this.gameInfoService = gameInfoService;
        this.userService = userService;
        this.playerService = playerService;
        this.roundService = roundService;
        this.gameService = gameService;
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameInfoDTO>> getAllGames() {
        List<GameInfo> games = gameInfoService.findAllGameInfo();
        List<GameInfoDTO> gameInfoDTOs = games.stream()
                .map(game -> new GameInfoDTO(game))
                .collect(Collectors.toList());

        return new ResponseEntity<>(gameInfoDTOs, HttpStatus.OK);
    }
    
    @GetMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GameInfoDTO> getGameInfoByGameId(@RequestParam Integer gameId) {
        GameInfo game = gameInfoService.findGameInfoByGameId(gameId);
        GameInfoDTO gameInfoDTO = new GameInfoDTO(game);
        return new ResponseEntity<>(gameInfoDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<GameInfoDTO> createGameInfo(@Valid @RequestBody GameInfoDTO gameInfoDTO) throws DataAccessException {

        GameInfo newGameInfo = new GameInfo();
        Integer gameId = gameInfoDTO.getGameId();
        Optional<Game> game = gameService.getGameById(gameId);
        if(game.isPresent()){
            // AHORA QUE NO ESTA GAME REFACTORIZADO
            GameMode gameMode = game.get().getGameMode();
            Integer numPlayers = game.get().getNumPlayers();
            Integer gameTime = game.get().getGameTime();
            Integer winner = game.get().getWinner();
            Integer creatorId = game.get().getCreator().getId();
            GameStatus  status = game.get().getStatus();
            Player creator = playerService.getPlayerById(creatorId).get();
            newGameInfo.setGame(game.get());
            newGameInfo.setGameMode(gameMode);
            newGameInfo.setNumPlayers(numPlayers);
            newGameInfo.setGameTime(gameTime);
            newGameInfo.setWinner(winner);
            newGameInfo.setCreator(creator);
            newGameInfo.setStatus(status);
            gameInfoService.saveGameInfo(newGameInfo);
            return new ResponseEntity<>(new GameInfoDTO(newGameInfo), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("ready/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GameInfoDTO> updatePlayersCount(@PathVariable("gameId") Integer gameId) {
        GameInfo toUpdate = gameInfoService.findGameInfoByGameId(gameId);
        Integer currentNumPlayers = toUpdate.getNumPlayers();
        // Restar un jugador si el número actual es mayor o igual a 0
        if (currentNumPlayers >= 0) {
            toUpdate.setNumPlayers(currentNumPlayers - 1);
            GameInfo savedGameInfo = gameInfoService.saveGameInfo(toUpdate);
            return new ResponseEntity<>(new GameInfoDTO(savedGameInfo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
