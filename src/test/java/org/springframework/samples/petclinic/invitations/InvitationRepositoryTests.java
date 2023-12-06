package org.springframework.samples.petclinic.invitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.invitation.Invitation;
import org.springframework.samples.petclinic.invitation.InvitationRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testfindPendigInvitation(){
        List<Invitation> invitations = invitationRepository.findPendigInvitation("Alvaro2");
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Caso negativo
    @Test
    public void testfindNoPendigInvitation(){
        List<Invitation> invitations = invitationRepository.findPendigInvitation("Alvaro200");
        assertNotNull(invitations);
        assertTrue(invitations.size() == 0);
    }
    //Encontrar si cierto usuario ya recibio la invitacion
    @Test
    public void testfindAlreadyPendigInvitation(){
        List<Invitation> invitations = invitationRepository.findAlreadyPendigInvitation("Alvaro2");
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Caso negativo
    @Test
    public void testNofindAlreadyPendigInvitation(){
        List<Invitation> invitations = invitationRepository.findAlreadyPendigInvitation("Alvaro200");
        assertNotNull(invitations);
        assertTrue(invitations.size() == 0);
    }
    //Encontrar las invitaciones aceptadas por cierto user
    @Test
    public void testfindAcceptedInvitation(){
        List<Invitation> invitations = invitationRepository.findAcceptedInvitation("Guille8");
        assertNotNull(invitations);
        assertTrue(invitations.size() > 0);
    }
    //Caso negativo
    @Test
    public void testfindNoAcceptedInvitation(){
        List<Invitation> invitations = invitationRepository.findAcceptedInvitation("Guille80");
        assertNotNull(invitations);
        assertTrue(invitations.size() == 0);
    }

}
