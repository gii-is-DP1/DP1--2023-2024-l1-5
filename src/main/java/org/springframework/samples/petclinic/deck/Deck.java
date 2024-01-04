package org.springframework.samples.petclinic.deck;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.round.Round;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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

    @OneToOne()
	@JoinColumn(name = "round_id", referencedColumnName = "id")
    @JsonIgnore
    private Round round; 


    //@JsonIgnore
    @OneToMany()
    private List<Card> cards;
}
