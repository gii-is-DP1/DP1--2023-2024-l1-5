package org.springframework.samples.petclinic.invitation;

import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="invitations")
public class Invitation extends BaseEntity{

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")    
    private User destinationUser;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")    
    private User sourceUser;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "invitation_state", columnDefinition = "varchar(20) default 'PENDING'")
    private InvitationState state;
    
}
