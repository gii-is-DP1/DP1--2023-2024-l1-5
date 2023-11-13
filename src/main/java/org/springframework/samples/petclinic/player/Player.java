package org.springframework.samples.petclinic.player;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.samples.petclinic.game.Game;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends Person {

	@Column(name = "PLAYERUSERNAME", columnDefinition = "varchar(50) default 'exampleName'")
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
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "player_games", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "game_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "player_id", "game_id" }) })
	private List<Game> game_list;

	public void removeAllGames() {
		game_list = null;
	}

}