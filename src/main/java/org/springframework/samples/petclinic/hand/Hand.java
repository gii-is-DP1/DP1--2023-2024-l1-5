package org.springframework.samples.petclinic.hand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.round.Round;

@Entity
@Getter
@Setter
@Table(name = "hands")
public class Hand extends BaseEntity {
    
    @Column(name = "num_cards")
    @NotBlank
    private Integer numCartas;

    @OneToMany(mappedBy = "hand")
    @NotNull
    @Size(min = 1)
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "round_id")
    //@JsonIgnore
    private Round round;

}
