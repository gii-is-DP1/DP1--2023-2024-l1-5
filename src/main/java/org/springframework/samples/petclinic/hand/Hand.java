package org.springframework.samples.petclinic.hand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@Table(name = "hands")
public class Hand extends BaseEntity {

    @Column(name = "num_cards")
    private Integer numCartas;

    @ManyToOne
    @JoinColumn(name = "round_id")
    @JsonIgnore
    private Round round;

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")  
    private Player player;

    @OneToMany()
    private List<Card> cards;
}
