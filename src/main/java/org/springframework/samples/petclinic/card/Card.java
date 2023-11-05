package org.springframework.samples.petclinic.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.symbol.Symbol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(name = "image")
    @NotBlank
    @NotNull
    private String image;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "hand_id")
    @JsonIgnore
    private Hand hand;
 
    @ManyToMany
    @NotBlank
    @NotNull 
    @JoinTable(name = "card_symbols", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "symbol"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "card_id", "symbol" }) })
    @Size(min = 6, max = 6)
    private List<Symbol> symbols;
    
    @ManyToOne
    @JoinColumn(name = "deck_id")
    @JsonIgnore
    private Deck deck;

}
