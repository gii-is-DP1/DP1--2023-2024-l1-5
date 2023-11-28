package org.springframework.samples.petclinic.achievement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import java.util.List;

import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.player.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class Achievement extends NamedEntity {
    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "threshold")
    @Min(0)
    private double threshold;
    
    @Column(name = "metric")
    @Enumerated(EnumType.STRING)
    @NotNull
    Metric metric;
    /*
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
    */

    @ManyToMany
    @JoinTable(name = "player_achievements", joinColumns = @JoinColumn(name = "achievement_id"), inverseJoinColumns = @JoinColumn(name = "player_id"), uniqueConstraints = {
    @UniqueConstraint(columnNames = { "player_id","achievement_id" }) })
    @JsonIgnore
    private List<Player> players;

    public String getActualDescription(){
        return description.replace("<THRESHOLD>",String.valueOf(threshold));
    }
}
