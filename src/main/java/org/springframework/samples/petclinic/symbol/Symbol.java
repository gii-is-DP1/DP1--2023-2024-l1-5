package org.springframework.samples.petclinic.symbol;

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
import org.springframework.samples.petclinic.card.Card;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

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

    @ManyToMany(mappedBy = "symbols")
    @JsonIgnore
    private List<Card> cards;
    
}
