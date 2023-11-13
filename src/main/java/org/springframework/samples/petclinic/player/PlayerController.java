package org.springframework.samples.petclinic.player;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.samples.petclinic.vet.Vet;
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
    public ResponseEntity<List<Player>>getAllPlayers(){
        return  new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> getPlayerById(@PathVariable("id")Integer id){
        Optional<Player> p=playerService.getPlayerById(id);
        if(!p.isPresent())
            throw new ResourceNotFoundException("Player", "id", id);
        return new ResponseEntity<>(p.get(), HttpStatus.OK);
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
		RestPreconditions.checkNotNull(playerService.getPlayerById(playerId), "Vet", "ID", playerId);
		return new ResponseEntity<>(this.playerService.updatePlayer(player, playerId), HttpStatus.OK);
	}



}
