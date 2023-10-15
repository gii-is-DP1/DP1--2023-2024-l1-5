package org.springframework.samples.petclinic.round;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.EnumType;
import jakarta.persistence.Temporal;

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

}