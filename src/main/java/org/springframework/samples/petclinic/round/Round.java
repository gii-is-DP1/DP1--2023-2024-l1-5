package org.springframework.samples.petclinic.round;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.EnumType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

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

    // IMPORTANTE: COMPROBAR
    // @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Hand> hands;

    // @OneToMany(mappedBy = "round", fetch = FetchType.EAGER)
    // @Size(min = 2, max = 8)
    // // @JsonIgnore
    // private List<Player> players;

}