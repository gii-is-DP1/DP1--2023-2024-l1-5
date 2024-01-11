package org.springframework.samples.petclinic.invitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.invitation.Invitation;
import org.springframework.samples.petclinic.invitation.InvitationRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest()
public class InvitationRepositoryTests {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    InvitationRepository invitationRepository;


    //Encontrar todas las invitaciones realozadas
    @Test
    public void testFindAll() {
        List<Invitation> invitations = invitationRepository.findAll();
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Encontrar las invitaciones en estado PENDING de cierto user
    @Test
    public void testfindPendingInvitation(){
        List<Invitation> invitations = invitationRepository.findPendingInvitation("lucantdel");
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Caso negativo
    @Test
    public void testfindNoPendingInvitation(){
        List<Invitation> invitations = invitationRepository.findPendingInvitation("ronmonalb");
        assertNotNull(invitations);
        assertTrue(invitations.size() == 0);
    }
    //Encontrar si cierto usuario ya recibio la invitacion
    @Test
    public void testfindAlreadyPendingInvitation(){
        List<Invitation> invitations = invitationRepository.findAlreadyPendingInvitation("ronmonalb");
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Caso negativo
    @Test
    public void testNofindAlreadyPendingInvitation(){
        List<Invitation> invitations = invitationRepository.findAlreadyPendingInvitation("lucantdel");
        assertNotNull(invitations);
        assertTrue(invitations.size() == 0);
    }
    //Encontrar las invitaciones aceptadas por cierto user
    @Test
    public void testfindAcceptedInvitation(){
        List<Invitation> invitations = invitationRepository.findAcceptedInvitation("manortper");
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Caso negativo
    @Test
    public void testfindNoAcceptedInvitation(){
        List<Invitation> invitations = invitationRepository.findAcceptedInvitation("ronmonalb");
        assertNotNull(invitations);
        assertTrue(invitations.size() == 0);
    }

}
