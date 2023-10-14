package org.springframework.samples.petclinic.statistic;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "player_statistics")
public class PlayerStatistic extends BaseEntity {
    @Column(name = "number_of_games")
    @NotNull
    private Integer number_of_games;

    @JoinColumn(name = "win_number")
    @NotNull
    private Integer win_number;

    @JoinColumn(name = "lose_number")
    @NotNull
    private Integer lose_number;

    @JoinColumn(name = "competitive_points")
    @NotNull
    private Integer competitive_points; 

    @JoinColumn(name = "avg_duration")
    @NotNull
    private Integer avg_duration;// Duration in Seconds

    @JoinColumn(name = "max_duration")
    @NotNull
    private Integer max_duration;// Duration in Seconds

    @JoinColumn(name = "min_duration")
    @NotNull
    private Integer min_duration;// Duration in Seconds
}
