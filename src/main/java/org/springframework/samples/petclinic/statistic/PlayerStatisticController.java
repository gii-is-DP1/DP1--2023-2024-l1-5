package org.springframework.samples.petclinic.statistic;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.samples.petclinic.player.Player;

@RestController
@RequestMapping("/api/v1/playersStatistic")
@Tag(name = "PlayerStatistics", description = "The Player Statistics management API")
@SecurityRequirement(name = "bearerAuth")
public class PlayerStatisticController {

    private final PlayerStatisticService playerStatisticService;
    private final PlayerService playerService;

    @Autowired
    public PlayerStatisticController(PlayerStatisticService playerStatisticService, PlayerService playerService){
        this.playerStatisticService = playerStatisticService;
        this.playerService = playerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlayerSDTO>> getAllGameS() {
        List<PlayerStatistic> playerStatistics = playerStatisticService.getAllGameS();
        List<PlayerSDTO> playerSDTOs = playerStatistics.stream()
            .map(p -> new PlayerSDTO(p))
            .collect(Collectors.toList());

        return new ResponseEntity<>(playerSDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlayerSDTO> getGameSById(@PathVariable("id") Integer id) {
        PlayerStatistic playerStatistic = playerStatisticService.getPSById(id);
        PlayerSDTO playerSDTO = new PlayerSDTO(playerStatistic);
        return new ResponseEntity<>(playerSDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PlayerSDTO> createGameS(@Valid @RequestBody PlayerSRequest playerSRequest) throws DataAccessException {
        PlayerStatistic playerStatistic = new PlayerStatistic();
        Player player = playerService.getPlayerById(playerSRequest.getPlayerId()).get();

        playerStatistic.setMin_duration(playerSRequest.getMinD());
        playerStatistic.setMax_duration(playerSRequest.getMaxD());
        playerStatistic.setLose_number(playerSRequest.getLoseN());
        playerStatistic.setWin_number(playerSRequest.getWinN());
        playerStatistic.setCompetitive_points(playerSRequest.getPuntosC());
        playerStatistic.setNumber_of_games(playerSRequest.getNumberG());
        playerStatistic.setPlayer(player);
        PlayerStatistic result = playerStatisticService.save(playerStatistic); 
        PlayerSDTO playerSDTO = new PlayerSDTO(result);       

        return new ResponseEntity<>(playerSDTO, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PlayerStatistic> updateGameS(@PathVariable("id") Integer id,
            @Valid @RequestBody PlayerSRequestPUT playerSRequest) throws DataAccessException {
        PlayerStatistic ps = playerStatisticService.getPSById(id);

        BeanUtils.copyProperties(playerSRequest, ps, "id", "player");
        if(playerSRequest.getNumberG() != null && playerSRequest.getNumberG() != 0 ){
            ps.setNumber_of_games(playerSRequest.getNumberG());
        }
        if(playerSRequest.getWinN() != null && playerSRequest.getWinN() != 0 ){
            ps.setWin_number(playerSRequest.getWinN());
        }
        if(playerSRequest.getLoseN() != null && playerSRequest.getLoseN() != 0 ){
            ps.setLose_number(playerSRequest.getLoseN());
        }
        if(playerSRequest.getPuntosC() != null && playerSRequest.getPuntosC() != 0 ){
            ps.setCompetitive_points(playerSRequest.getPuntosC());
        }
        if(playerSRequest.getMaxD() != null && playerSRequest.getMaxD() != 0 ){
            ps.setMax_duration(playerSRequest.getMaxD());
        }
        if(playerSRequest.getMinD() != null && playerSRequest.getMinD() != 0 ){
            ps.setMin_duration(playerSRequest.getMinD());
        }

        PlayerStatistic result = playerStatisticService.update(ps);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    
    
}
