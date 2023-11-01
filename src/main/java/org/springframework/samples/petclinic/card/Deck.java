package org.springframework.samples.petclinic.card;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "deck")
public class Deck extends BaseEntity {

    @Column(name = "number_of_cards")
    @NotNull
    private Integer numberOfCards;
}
