package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Players", description = "The Player management API")
@SecurityRequirement(name = "bearerAuth")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<List<Player>>getAllPlayers(){
    // return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    // }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers(); // Obtener la lista de objetos Player
        List<PlayerDTO> playerDTOs = players.stream()
                .map(player -> new PlayerDTO(player)) // Utilizar el constructor de PlayerDTO
                .collect(Collectors.toList());

        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("id") Integer id) {
        Optional<Player> p = playerService.getPlayerById(id);
        if (!p.isPresent())
            throw new ResourceNotFoundException("Player", "id", id);
        return new ResponseEntity<>(new PlayerDTO(p.get()), HttpStatus.OK);
    }
    @GetMapping("/user/{id}") 
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> getPlayerByUserId(@PathVariable("id")Integer id){
        Player p=playerService.getPlayerByUserId(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }


}
