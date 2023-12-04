package org.springframework.samples.petclinic.invitation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationDTO {

    String destination_user;
    String source_user;
    InvitationState invitation_state;
    Integer game_id;

    public InvitationDTO(){}

    public InvitationDTO(Invitation i){
        this.destination_user = i.getDestination_user();
        this.source_user = i.getSource_user();
        this.invitation_state = i.getInvitation_state();
        this.game_id = i.getGame().getId();
    }

    
}
