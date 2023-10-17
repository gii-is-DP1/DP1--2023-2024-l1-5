package org.springframework.samples.petclinic.invitation;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="invitations")
public class Invitation extends BaseEntity{

    @NotNull
    @Column(name = "destination_user")    
    private String destination_user;

    @NotNull
    @Column(name = "source_user")    
    private String source_user;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "invitation_state")
    private InvitationState invitation_state;
    
}
