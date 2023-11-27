package org.springframework.samples.petclinic.friendship;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friendship")
public class Friendship  extends BaseEntity{
    
    @ManyToOne(optional = false)
    private Player user_source;

    @ManyToOne(optional = false)
    private Player user_dst;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "friend_request_status", columnDefinition = "varchar(20)")
	private FriendshipStatus status;
    
}
