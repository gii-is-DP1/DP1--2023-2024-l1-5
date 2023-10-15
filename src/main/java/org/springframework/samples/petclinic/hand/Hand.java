package org.springframework.samples.petclinic.hand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "hand")
public class Hand extends BaseEntity {
    @Column(name = "num_cartas")
    @NotBlank
    private Integer numCartas;
    
}
