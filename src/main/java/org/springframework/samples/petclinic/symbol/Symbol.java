package org.springframework.samples.petclinic.symbol;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="symbols")
public class Symbol {
    @NotBlank
    @Id
    @NotNull
    @Enumerated(EnumType.STRING)
    Name name;


    // @ManyToMany()
    // List<Card> cards;
}
