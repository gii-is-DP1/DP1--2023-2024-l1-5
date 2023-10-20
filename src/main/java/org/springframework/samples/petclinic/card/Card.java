package org.springframework.samples.petclinic.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.model.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "card")
public class Card extends BaseEntity {

    @Column(name = "image")
    @NotBlank
    @NotNull
    private String image;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "hand_id")
    private Hand hand;

    
}
