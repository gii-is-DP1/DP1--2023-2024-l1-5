package org.springframework.samples.petclinic.statistic;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.player.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Getter
@Setter
@Entity
@Table(name = "player_statistics")
public class PlayerStatistic extends BaseEntity {

    @NotNull
    @Column(name = "number_of_games")
    private Integer number_of_games;

    @NotNull
    @Column(name = "win_number")
    private Integer win_number;

    @NotNull
    @Column(name = "lose_number")
    private Integer lose_number;

    @NotNull
    @Column(name = "competitive_points")
    private Integer competitive_points; 

    @NotNull
    @Column(name = "max_duration")
    private Integer max_duration;// Duration in Seconds

    @NotNull
    @Column(name = "min_duration")
    private Integer min_duration;// Duration in Seconds

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @JsonIgnore
    private Player player;
}
