package org.springframework.samples.petclinic.invitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Invitation", description = "The Invitations management API")
@SecurityRequirement(name = "bearerAuth")
public class InvitationController {

    InvitationService invitationService;
    UserService userService;
    PlayerService playerService;
    GameService gameService;
    private static final String PLAYER_AUTH = "PLAYER";
    
    @Autowired
    public InvitationController(InvitationService invitationService,UserService userService,PlayerService playerService, GameService gameService){
        this.invitationService = invitationService;
        this.userService = userService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/api/v1/invitations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Invitation>> getAllInvitations(){
        return new ResponseEntity<>(invitationService.getAllInvitations(), HttpStatus.OK);
    }
    @GetMapping("/api/v1/invitations/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Invitation>> getPendingInitations(@PathVariable("username") String username){
        Player player = playerService.getPlayerByUsername(username);
        return new ResponseEntity<>(invitationService.getPendigInvitationsReceived(player.getPlayerUsername()), HttpStatus.OK);
    }
    
    @PostMapping("/api/v1/games/{game_id}/invitations/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Invitation> createInvitation(@PathVariable("username") String username, @PathVariable("game_id") Integer game_id){
        User user = userService.findCurrentUser();
        Game game = gameService.getGameById(game_id).get();
        Invitation newInvitation = new Invitation();
        String playerDst = playerService.getPlayerByUsername(username).getPlayerUsername();
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)){
            Player player = playerService.findPlayerByUser(user);
            newInvitation.setSource_user((player.getPlayerUsername()));
            newInvitation.setDestination_user(playerDst);
            newInvitation.setInvitation_state(InvitationState.PENDING);
            newInvitation.setGame(game);
            return new ResponseEntity<>(invitationService.saveInvitation(newInvitation, "POST"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
 

    @PutMapping("/api/v1/invitations/acceptRequest/{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Invitation> acceptInvitation(@PathVariable("invitationId") Integer invitationId){
        User user = userService.findCurrentUser();
        Player player = playerService.findPlayerByUser(user);
        Invitation invitation  = invitationService.getInvitationById(invitationId);
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true) && player.getPlayerUsername() == invitation.getDestination_user() && invitation.getInvitation_state() == InvitationState.PENDING){
            invitation.setInvitation_state(InvitationState.ACCEPTED);
            return new ResponseEntity<>(invitationService.saveInvitation(invitation,"PUT"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/api/v1/invitations/refusededRequest/{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Invitation> refuseInvitation(@PathVariable("invitationId") Integer invitationId){
        User user = userService.findCurrentUser();
        Player player = playerService.findPlayerByUser(user);
        Invitation invitation  = invitationService.getInvitationById(invitationId);
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true) && player.getPlayerUsername() == invitation.getDestination_user() && invitation.getInvitation_state() == InvitationState.PENDING){
            invitation.setInvitation_state(InvitationState.REFUSED);
            return new ResponseEntity<>(invitationService.saveInvitation(invitation,"PUT"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
