package org.springframework.samples.petclinic.hand;


import java.util.List;

import org.springframework.samples.petclinic.card.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandDTO {

    Integer numCartas;
    List<Card> cards;
    Integer round;
    Integer player;

    public HandDTO(){}

    public HandDTO(Hand h){

        this.numCartas = h.getNumCartas();
        this.cards = h.getCards();
        this.round = h.getRound().getId();
        this.player = h.getPlayer().getId();
    }
    
}
