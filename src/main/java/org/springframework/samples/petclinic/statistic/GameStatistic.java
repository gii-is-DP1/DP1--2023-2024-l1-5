package org.springframework.samples.petclinic.statistic;
import org.springframework.samples.petclinic.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="game_statistics")
public class GameStatistic extends BaseEntity{
    @NotNull
    @NotBlank
    private String result;
    @NotNull
    private Integer game_duration;
    @NotNull
    private Integer points;

}
