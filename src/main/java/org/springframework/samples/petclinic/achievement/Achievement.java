package org.springframework.samples.petclinic.achievement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import org.springframework.samples.petclinic.model.NamedEntity;

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
    public String getActualDescription(){
        return description.replace("<THRESHOLD>",String.valueOf(threshold));
    }
}
