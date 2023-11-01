package org.springframework.samples.petclinic.game;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.round.Round;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
    
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "game_mode", columnDefinition = "varchar(20)")
    private GameMode gameMode;

    @Column(name = "num_players")
    private Integer numPlayers;

    @Column(name = "winner_id")
    private Integer winner;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private Player creator;

    @Column(name = "game_time")
    private Integer gameTime;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "game_status", columnDefinition = "varchar(20)")
	private GameStatus status;

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Size(min = 1, max = 5)
    // @JsonIgnore // PARA EVITAR LA RECUSIVIDAD INFINITA EN SWAGGER
    private List<Round> rounds;

    @ManyToMany
    private List<Player> players;

    public void setGameStatus(GameStatus status) {
        this.status = status;


        }
        }
