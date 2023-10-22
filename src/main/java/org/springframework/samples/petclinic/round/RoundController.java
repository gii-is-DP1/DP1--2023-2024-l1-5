package org.springframework.samples.petclinic.round;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.petclinic.round.exceptions.WaitingGameException;


import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rounds")
@Tag(name = "Rounds", description = "The Round management API")
@SecurityRequirement(name = "bearerAuth")
public class RoundController {
    private final GameService gameService;
    private final UserService userService;
    private final PlayerService playerService;
    private final RoundService roundService;
    private static final String PLAYER_AUTH = "PLAYER";

    @Autowired
    public RoundController(RoundService roundService, UserService userService, GameService gameService, PlayerService playerService) {
        this.roundService = roundService;
        this.userService = userService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Round>> getAllRounds() {
        return new ResponseEntity<>(roundService.getAllRounds(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Round> getRoundById(Integer id) {
        Optional<Round> r = roundService.getRoundById(id);
        if (!r.isPresent())
            throw new ResourceNotFoundException("Round", "id", id);
        return new ResponseEntity<>(r.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Round> createRound(@Valid @RequestBody RoundRequest roundRequest) throws DataAccessException{
        //POR AHORA NO SE TIENE EN CUENTA SI ES COMPETITIVO O NO
        
        User user = userService.findCurrentUser();
        Round newRound = new Round();
        Round savedRound;
        BeanUtils.copyProperties(roundRequest, newRound, "id");
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            Player player = playerService.findPlayerByUser(user);
            boolean hasWaitingGame = gameService.getWaitingGame(player) != null;
            if(!hasWaitingGame){
                throw new WaitingGameException("No hay ninguna partida en espera");
            }else{
                newRound.setRoundMode(roundRequest.getRoundMode());
                Optional<Game> waitingGame = gameService.getWaitingGame(player);
                if(waitingGame.isPresent()){
                    newRound.setGame(waitingGame.get());
                    savedRound = roundService.saveRound(newRound);
                }else{
                    throw new WaitingGameException("No hay ninguna partida en espera");
                }
            }
        }else{
            savedRound = roundService.saveRound(newRound);
        }
        return new ResponseEntity<>(savedRound, HttpStatus.CREATED);
    }

}
