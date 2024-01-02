package org.springframework.samples.petclinic.auth.payload.request;

import jakarta.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.player.State;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	
	// User
	@NotBlank
	private String username;
	
	@NotBlank
	private String authority;

	@NotBlank
	private String password;
	
	//Both
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	private String playerUsername;
	private String image;
	private State state;

	private String address;
	private String city;
	private String telephone;
	private Clinic clinic;

}
