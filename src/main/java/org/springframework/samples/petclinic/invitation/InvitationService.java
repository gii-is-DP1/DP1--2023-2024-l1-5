package org.springframework.samples.petclinic.invitation;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.InvitationAlreadySent;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InvitationService {

    InvitationRepository invitationRepository;
    
    @Autowired
    public InvitationService(InvitationRepository invitationRepository){
        this.invitationRepository = invitationRepository;
    }

    @Transactional(readOnly = true)
    public List<Invitation> getAllInvitations(){
        return invitationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Invitation getInvitationById(Integer id){
        return invitationRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Invitation> getPendingInvitationsSent(String playerUsername){
         return invitationRepository.findPendingInvitation(playerUsername);
    }

    @Transactional(readOnly = true)
    public List<Invitation> getPendingInvitationsReceived(String playerUsername){
        return invitationRepository.findAlreadyPendingInvitation(playerUsername);
    }

    @Transactional(readOnly = true)
    public List<Invitation> getAllInvitationsByGameId(Integer gameId){
        return invitationRepository.findAllByGameId(gameId);
    }

    @Transactional
	public void deleteInvitation(Invitation i) throws DataAccessException {	
		invitationRepository.delete(i);
	}



    @Transactional(rollbackFor = {InvitationAlreadySent.class})
    public Invitation saveInvitation(Invitation invitation, String type){
        String playerSource = invitation.getSource_user();
        String playerDst = invitation.getDestination_user();
        List<Invitation> invitations = invitationRepository.findPendingInvitation(playerSource);
        if(type=="POST"){
            for (Invitation i : invitations) {
                if (i.getDestination_user() == playerDst) {
                    throw new InvitationAlreadySent("Invitation already already sent");
                }
            }
        }
        invitationRepository.save(invitation);
        return invitation;
    }


}
