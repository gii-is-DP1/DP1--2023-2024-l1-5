package org.springframework.samples.petclinic.invitation;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.InvitationAlreadySent;
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
    public List<Invitation> getPendigInvitations(String playerUsername){
         return invitationRepository.findPendigInvitation(playerUsername);
    }

    @Transactional(rollbackFor = {InvitationAlreadySent.class})
    public Invitation saveInvitation(Invitation invitation, String type){
        String playerSource = invitation.getSource_user();
        String playerDst = invitation.getDestination_user();
        List<Invitation> invitations = invitationRepository.findPendigInvitation(playerSource);
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

    // @Transactional()
    // public Game updateGame(int idPlayer, int idGame){
    //     Player toAddPlayer = playerRepository.findPlayerById(idPlayer).get();
    //     Game toUpdate= getGameById(idGame).get();
    //     toUpdate.getPlayers().add(toAddPlayer);
    //     User user = userService.findCurrentUser();
    //     Player p = playerService.findPlayerByUser(user);
    //     return saveGame(toUpdate,p);
    // } 

    // @Transactional
    // public Game saveGame(Game game, Player player) {
    //     User user = userService.findCurrentUser();
    //     Player p = playerService.findPlayerByUser(user);
    //     boolean hasActiveGame = hasActiveGame(p);
    //     if(hasActiveGame){
    //         throw new ActiveGameException("El jugador ya tiene una partida activa");
    //     }
    //     gameRepository.save(game);
    //     return game;
    // }
}
