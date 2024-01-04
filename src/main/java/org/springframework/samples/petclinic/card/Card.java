package org.springframework.samples.petclinic.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.symbol.Symbol;
import jakarta.persistence.ManyToMany;

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

    @ManyToMany
    @JoinTable(name = "card_symbols")
    private List<Symbol> symbols;

    // @ManyToOne
    // @JoinColumn(name = "deck_id")
    // @JsonIgnore
    // private Deck deck;

    // @ManyToOne
    // @JoinColumn(name = "hand_id")
    // @JsonIgnore
    // private Hand hand;
    
}
