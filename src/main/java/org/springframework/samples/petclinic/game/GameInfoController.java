package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/v1/gameInfo")
@Tag(name = "GameInfo", description = "The GameInfo management API")
@SecurityRequirement(name = "bearerAuth")
public class GameInfoController {

    private final GameInfoService gameInfoService;


    public GameInfoController(GameInfoService gameInfoService) {
        this.gameInfoService = gameInfoService;

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
    public ResponseEntity<GameInfoDTO> getGameInfoByGameId(@PathVariable("gameId") Integer gameId) {
        GameInfo game = gameInfoService.findGameInfoByGameId(gameId);
        GameInfoDTO gameInfoDTO = new GameInfoDTO(game);
        return new ResponseEntity<>(gameInfoDTO, HttpStatus.OK);
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
