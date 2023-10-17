package org.springframework.samples.petclinic.achievement;

import org.springframework.samples.petclinic.model.NamedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String threshold;

    @Column(name = "metric")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Metric metric;

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
