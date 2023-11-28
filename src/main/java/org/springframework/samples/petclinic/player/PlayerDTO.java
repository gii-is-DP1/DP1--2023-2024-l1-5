package org.springframework.samples.petclinic.player;


import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    Integer id;
    String playerUsername;
    State state;
    String lastName;
    String firstName;
    String image;
    User user;
    //Integer hand;//esto tmb

    public PlayerDTO(){}

    public PlayerDTO(Player p){
        
        this.id = p.getId();
        this.playerUsername = p.getPlayerUsername();
        this.state = p.getState();
        this.lastName = p.getLastName();
        this.firstName = p.getFirstName();
        this.image = p.getImage();
        this.user = p.getUser();
         //this.hand = p.getHand().getId();
    }
    
}
