package org.springframework.samples.petclinic.friend_Request;

import org.springframework.samples.petclinic.model.BaseEntity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Notblank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friend_requests")
public class Friend_Request extends BaseEntity{

    @Column(name = "user_source")
    @NotNull
    private String user_source;

    @Column(name = "user_dst")
    @NotNull
    private String user_dst;

    @Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "friend_request_status", columnDefinition = "varchar(20)")
	private Friend_RequestStatus status;
}