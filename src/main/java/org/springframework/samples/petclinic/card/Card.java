package org.springframework.samples.petclinic.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.symbol.Symbol;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "card")
public class Card extends BaseEntity {

    @Column(name = "image")
    @NotBlank
    @NotNull
    private String image;
 
    @ManyToMany
    @NotBlank
    @NotNull 
    @JoinTable(name = "card_symbol", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "symbol"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "card_id", "symbol" }) })
    @Size(min = 8, max = 8)
    private List<Symbol> symbols;

}
