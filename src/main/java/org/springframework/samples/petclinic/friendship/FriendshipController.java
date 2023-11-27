package org.springframework.samples.petclinic.friendship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;

@RestController
@RequestMapping("/api/v1/friendship")
@Tag(name = "Friendship", description = "The Friendship management API")
@SecurityRequirement(name = "bearerAuth")
public class FriendshipController {

    FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService){
        this.friendshipService = friendshipService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Friendship>> getAllFriendships(){
        return new ResponseEntity<>(friendshipService.getAllFriendships(), HttpStatus.OK);
    }

    @GetMapping("/accepted/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Friendship>> getAcceptedFriendshipsByPlayerId(@PathVariable("playerId") Integer playerId){
        return new ResponseEntity<>(friendshipService.getAcceptedFriendshipsByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/friends/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Player>> getFriendsByPlayerId(@PathVariable("playerId") Integer playerId){
        return new ResponseEntity<>(friendshipService.getFriendsByPlayerId(playerId), HttpStatus.OK);
    }

}