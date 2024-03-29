package org.springframework.samples.petclinic.player;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends Person {

	@Column(name = "PLAYERUSERNAME", columnDefinition = "varchar(50) default 'exampleName'")
	private String playerUsername;


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
}