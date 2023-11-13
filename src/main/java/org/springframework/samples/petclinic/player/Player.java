package org.springframework.samples.petclinic.player;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.user.User;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.Set; 
import java.util.HashSet;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.samples.petclinic.game.Game;


@Getter
@Setter
@Entity
@Table(name="players")
public class Player extends Person{

	@Column(name = "image")
    @NotBlank
    @NotNull
    private String image;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(columnDefinition = "varchar(20) default 'ACTIVE'")
	State state;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

    @ManyToMany
    private Set<Player> friendsList = new HashSet<>();

	@ManyToMany(mappedBy = "players")
	@JsonIgnore
	private List<Game> game;
  
	//IMPORTANTE: COMPROBAR QUE FUNCIONA ESTA RELACIÓN
	
	// @ManyToMany(mappedBy = "players")
    // private List<Hand> hands;


}