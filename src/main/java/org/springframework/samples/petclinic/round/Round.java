package org.springframework.samples.petclinic.round;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.EnumType;
import jakarta.persistence.Temporal;
import java.util.List;
import org.springframework.samples.petclinic.player.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.OneToMany;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "rounds")
public class Round extends BaseEntity{

    @Column(name = "winner_id")
    @NotNull
    private Integer winner;

    @Column(name = "loser_id")
    @NotNull
    private Integer loser;

    @Column(name = "round_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Time roundTime;

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