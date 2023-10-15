package org.springframework.samples.petclinic.hand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hand")
public class Hand {
    @Column(name = "num_cartas")
    @NotBlank
    @Id
    private Integer numCartas;
    
}
