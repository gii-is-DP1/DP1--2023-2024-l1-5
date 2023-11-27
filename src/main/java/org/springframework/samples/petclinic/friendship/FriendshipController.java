package org.springframework.samples.petclinic.friendship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.FriendshipExistsException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/friendship")
@Tag(name = "Friendship", description = "The Friendship management API")
@SecurityRequirement(name = "bearerAuth")
public class FriendshipController {

    FriendshipService friendshipService;
    PlayerService playerService;
    UserService userService;
    private static final String PLAYER_AUTH = "PLAYER";

    @Autowired
    public FriendshipController(FriendshipService friendshipService, PlayerService playerService, UserService userService){
        this.friendshipService = friendshipService;
        this.playerService = playerService;
        this.userService = userService;
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
    public ResponseEntity<List<Player>> getFriends(@PathVariable("playerId") Integer playerId){
        return new ResponseEntity<>(friendshipService.getFriends(playerId), HttpStatus.OK);
    }

    @PostMapping("{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Friendship> createFriendship(@PathVariable("username") String username){
        User user = userService.findCurrentUser();
        Friendship newFriendship = new Friendship();
        Player playerDst = playerService.getPlayerByUsername(username);
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            Player player = playerService.findPlayerByUser(user);
            if (friendshipService.checkIfInvitationExists(player.getId(), playerDst.getId())) {
                throw new FriendshipExistsException("Ya existe una invitación entre estos jugadores.");
            }
            newFriendship.setUser_source(playerService.getPlayerById(player.getId()).get());
            newFriendship.setUser_dst(playerDst);
            newFriendship.setStatus(FriendshipStatus.WAITING);
            return new ResponseEntity<>(friendshipService.saveFriendship(newFriendship), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/requests")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Friendship>> getFriendshipRequests(){
        User user = userService.findCurrentUser();
        Player player = playerService.findPlayerByUser(user);
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            return new ResponseEntity<>(friendshipService.getFriendshipRequests(player.getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/acceptRequest/{friendshipId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Friendship> acceptFriendship(@PathVariable("friendshipId") Integer friendshipId){
        User user = userService.findCurrentUser();
        Player player = playerService.findPlayerByUser(user);
        Friendship friendship = friendshipService.getFriendshipById(friendshipId);
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true) && friendship.getUser_dst() == player ){
            friendship.setStatus(FriendshipStatus.ACCEPTED);
            return new ResponseEntity<>(friendshipService.saveFriendship(friendship), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/rejectRequest/{friendshipId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Friendship> rejectFriendship(@PathVariable("friendshipId") Integer friendshipId){
        User user = userService.findCurrentUser();
        Player player = playerService.findPlayerByUser(user);
        Friendship friendship = friendshipService.getFriendshipById(friendshipId);
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true) && friendship.getUser_dst() == player ){
            friendship.setStatus(FriendshipStatus.DENIED);
            return new ResponseEntity<>(friendshipService.saveFriendship(friendship), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}