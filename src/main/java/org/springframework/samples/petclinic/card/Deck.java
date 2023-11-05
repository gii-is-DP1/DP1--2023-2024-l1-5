package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.round.Round;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "decks")
public class Deck extends BaseEntity {

    @Column(name = "number_of_cards")
    @NotNull
    private Integer numberOfCards;

    @OneToMany(mappedBy = "deck", fetch = FetchType.EAGER)
    private List<Card> cards;

    @OneToOne(mappedBy = "deck", fetch = FetchType.EAGER)
    private Round round;

}
