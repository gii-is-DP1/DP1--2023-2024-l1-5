package org.springframework.samples.petclinic.hand;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.round.Round;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@Table(name = "hands")
public class Hand extends BaseEntity {
    
    @Column(name = "num_cards")
    @NotBlank
    private Integer numCartas;

    @ManyToMany(mappedBy = "hand")
    @NotNull
    @Size(min = 1)
    private List<Card> cards;

    @OneToOne
    @JoinColumn(name = "round_id")
    private Round round;


    
    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "player_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;

}
