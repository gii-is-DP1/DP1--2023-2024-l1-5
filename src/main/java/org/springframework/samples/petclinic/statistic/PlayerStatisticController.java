package org.springframework.samples.petclinic.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/v1/playersStatistic")
@Tag(name = "PlayerStatistics", description = "The Player Statistics management API")
@SecurityRequirement(name = "bearerAuth")
public class PlayerStatisticController {

    private final PlayerStatisticService playerStatisticService;

    @Autowired
    public PlayerStatisticController(PlayerStatisticService playerStatisticService){
        this.playerStatisticService = playerStatisticService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlayerStatistic>> getAllGameS() {
        return new ResponseEntity<>(playerStatisticService.getAllGameS(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlayerStatistic> getGameSById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(playerStatisticService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PlayerStatistic> createGameS(@Valid @RequestBody PlayerStatistic playerStatistic) throws DataAccessException {
        PlayerStatistic result = playerStatisticService.save(playerStatistic);        

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    
}
