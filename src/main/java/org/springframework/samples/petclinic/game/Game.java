package org.springframework.samples.petclinic.game;

import org.springframework.samples.petclinic.model.BaseEntity;

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
import java.util.List;
import jakarta.persistence.FetchType;

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
    @NotNull
    private Integer numPlayers;

    @Column(name = "winner_id")
    @NotNull
    private Integer winner;

    @Column(name = "creator_id")
    @NotNull
    private Integer creator;

    @Column(name = "game_time")
    @NotNull
    private Integer gameTime;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "game_status", columnDefinition = "varchar(20)")
	private GameStatus status;


    @OneToMany(mappedBy = "game", cascade= CascadeType.REMOVE,fetch = FetchType.EAGER)
    @Size(min = 1, max = 5)
    private List<Round> rounds;

}
