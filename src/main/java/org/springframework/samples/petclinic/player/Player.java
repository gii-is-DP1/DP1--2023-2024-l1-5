package org.springframework.samples.petclinic.player;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Set; 
import java.util.HashSet;
import java.util.List;

import org.springframework.samples.petclinic.round.Round;


@Getter
@Setter
@Entity
@Table(name="players")
public class Player extends BaseEntity{

    @Column(name= "PLAYERUSERNAME",columnDefinition = "varchar(50) default 'exampleName'")
	private String playerUsername;

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

	// @ManyToOne
	// @JoinColumn(name = "round_id")
	// @JsonIgnore
	// private Round round;

	//IMPORTANTE: COMPROBAR QUE FUNCIONA ESTA RELACIÓN
	
	// @ManyToMany(mappedBy = "players")
    // private List<Hand> hands;


}