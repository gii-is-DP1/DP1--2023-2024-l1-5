package org.springframework.samples.petclinic.invitations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.invitation.Invitation;
import org.springframework.samples.petclinic.invitation.InvitationRepository;
import org.springframework.samples.petclinic.invitation.InvitationService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.UserService;


@ExtendWith(MockitoExtension.class)
public class InvitationServiceTests {


    @Mock
    private UserService userService;

    @Mock   
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    @Mock
    private InvitationService invitationService;

    @Mock
    private InvitationRepository invitationRepository;


    @Test
    public void testGetAllInvitations() {
        invitationService = new InvitationService(invitationRepository);
        List<Invitation> invitations = new ArrayList<>();
        when(invitationRepository.findAll()).thenReturn(invitations);

        List<Invitation> result = invitationService.getAllInvitations();

        assertNotNull(result);
        assertEquals(invitations, result);
        verify(invitationRepository, times(1)).findAll();
    }

    @Test
    public void testgetInvitationById(){
        
        invitationService = new InvitationService(invitationRepository);
        Invitation invitation = new Invitation();
        Integer invitationId = 1;
        when(invitationRepository.findById(invitationId)).thenReturn(Optional.of(invitation));
        Invitation result = invitationService.getInvitationById(invitationId);

        assertNotNull(result);
        assertEquals(invitation, result);
        verify(invitationRepository, times(1)).findById(invitationId);
    }

    @Test
    public void testgetPendigInvitationsSent(){
        invitationService = new InvitationService(invitationRepository);
        List<Invitation> invitations = new ArrayList<>();
        when(invitationRepository.findPendigInvitation("Alvaro2")).thenReturn(invitations);
        
        List<Invitation> result = invitationService.getPendigInvitationsSent("Alvaro2");
        assertNotNull(result);
        assertEquals(invitations, result);
        verify(invitationRepository, times(1)).findPendigInvitation("Alvaro2");
    }

    @Test
    public void testgetPendigInvitationsReceived(){
        invitationService = new InvitationService(invitationRepository);
        List<Invitation> invitations = new ArrayList<>();
        when(invitationRepository.findAlreadyPendigInvitation("Alvaro2")).thenReturn(invitations);
        
        List<Invitation> result = invitationService.getPendigInvitationsReceived("Alvaro2");
        assertNotNull(result);
        assertEquals(invitations, result);
        verify(invitationRepository, times(1)).findAlreadyPendigInvitation("Alvaro2");
    }

    @Test
    public void testsaveInvitation(){

    }

    @Test
    public void saveFriendship(){
        Invitation i = createValidInvitation();
        try {
            invitationService.saveInvitation(i,"POST");
        } catch (Exception e) {
            fail("No exception should be thrown: "+ e.getMessage());
        }
    }

    
    private Invitation createValidInvitation(){
        Invitation invitation = new Invitation();
        Game game = new Game();
        invitation.setId(10);
        invitation.setSource_user("Alvaro2");
        invitation.setDestination_user("Guille8");
        invitation.setGame(game);
        return invitation;
    }


}
