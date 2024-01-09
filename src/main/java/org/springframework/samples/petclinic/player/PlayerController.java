package org.springframework.samples.petclinic.player;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Players", description = "The Player management API")
@SecurityRequirement(name = "bearerAuth")
public class PlayerController {

    private final PlayerService playerService;
    private final UserService userService;

    @Autowired
	public PlayerController(PlayerService playerService, UserService userService) {
		this.playerService = playerService;
        this.userService = userService;
	}
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
        PlayerDTO playerDTO = new PlayerDTO(p.get());
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }
    @GetMapping("/user/{id}") 
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> getPlayerByUserId(@PathVariable("id")Integer id){
        Player p=playerService.getPlayerByUserId(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Player> create(@RequestBody @Valid Player player) throws URISyntaxException {
		Player newPlayer = new Player();
		BeanUtils.copyProperties(player, newPlayer, "id");
		User user = userService.findCurrentUser();
		newPlayer.setUser(user);
		Player savedPlayer = this.playerService.savePlayer(newPlayer);

		return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
	}

    @PutMapping(value = "{playerId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Player> update(@PathVariable("playerId") int playerId, @RequestBody @Valid Player player) {
		RestPreconditions.checkNotNull(playerService.getPlayerById(playerId), "Player", "ID", playerId);
		return new ResponseEntity<>(this.playerService.updatePlayer(player, playerId), HttpStatus.OK);
	}

    
	@DeleteMapping(value = "{playerId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageResponse> delete(@PathVariable("playerId") int id) {
		RestPreconditions.checkNotNull(playerService.getPlayerByUserId(id), "Player", "ID", id);
		playerService.deletePlayer(id);
		return new ResponseEntity<>(new MessageResponse("Player deleted!"), HttpStatus.OK);
	}
}
