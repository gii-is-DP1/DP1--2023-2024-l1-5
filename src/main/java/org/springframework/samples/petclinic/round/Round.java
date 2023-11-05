package org.springframework.samples.petclinic.round;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.EnumType;


import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.samples.petclinic.card.Deck;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.hand.Hand;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rounds")
public class Round extends BaseEntity{

    @Column(name = "winner_id")
    private Integer winner;

    @Column(name = "loser_id")
    private Integer loser;

    @Column(name = "round_time")
    private Integer roundTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "round_mode", columnDefinition = "varchar(20)")
    private RoundMode roundMode;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    @OneToMany(mappedBy = "round", fetch = FetchType.EAGER)
    @Size(min = 2, max = 8)
    @JsonIgnore
    private List<Hand> hands;
    
    @OneToOne
    @NotNull
    @JoinColumn(name = "deck_id")
    @JsonIgnore
    private Deck deck;

}