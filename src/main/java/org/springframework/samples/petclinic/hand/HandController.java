package org.springframework.samples.petclinic.hand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.exceptions.ResourceNotOwnedException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hands")
@Tag(name = "Hands", description = "The Hand management API")
@SecurityRequirement(name = "bearerAuth")
public class HandController {

    private final HandService handService;
    private final UserService userService;
    private static final String PLAYER_AUTH = "PLAYER";

    @Autowired
    public HandController(HandService handService, UserService userService) {
        this.handService = handService;
        this.userService = userService;
    }

    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<List<Hand>> getAllHands() {
    // return new ResponseEntity<>(handService.getAllHands(), HttpStatus.OK);
    // }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HandDTO>> getAllHands() {
        List<Hand> hands = handService.getAllHands(); // Obtener la lista de objetos Hand
        List<HandDTO> handDTOs = hands.stream()
                .map(HandDTO::new) // Utilizar el constructor de HandDTO
                .collect(Collectors.toList());

        return new ResponseEntity<>(handDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hand> getHandById(@PathVariable("id")Integer id) {
        Optional<Hand> hand = handService.getHandById(id);
        if (!hand.isPresent()) {
            throw new ResourceNotFoundException("Hand" , "id" , id );
        }
        return new ResponseEntity<>(hand.get(), HttpStatus.OK);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Hand> putHand(@PathVariable("id")Integer id, @RequestBody @Valid HandDTO handDTO) {
    //     Hand aux = RestPreconditions.checkNotNull(handService.getHandById(id).get(), "hand", "id", id);
    //     User user = userService.findCurrentUser();

    //     if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
    //         Hand res = this.handService.updateHand(aux, id, null, null);
    //         return new ResponseEntity<>(res, HttpStatus.OK);
    //     }else{
    //         Hand res = this.handService.updateHand(aux, id, null, null);
    //         return new ResponseEntity<>(res, HttpStatus.OK);
    //     }
        
        

    // }

    // @PutMapping("{petId}")
	// @ResponseStatus(HttpStatus.OK)
	// public ResponseEntity<Pet> update(@PathVariable("petId") int petId, @RequestBody @Valid Pet pet) {
	// 	Pet aux = RestPreconditions.checkNotNull(petService.findPetById(petId), "Pet", "ID", petId);
	// 	User user = userService.findCurrentUser();
	// 	if (user.hasAuthority(OWNER_AUTH).equals(true)) {
	// 		Owner loggedOwner = userService.findOwnerByUser(user.getId());
	// 		Owner petOwner = aux.getOwner();
	// 		if (loggedOwner.getId().equals(petOwner.getId())) {
	// 			Pet res = this.petService.updatePet(pet, petId);
	// 			return new ResponseEntity<>(res, HttpStatus.OK);
	// 		} else
	// 			throw new ResourceNotOwnedException(aux);
	// 	} else {
	// 		Pet res = this.petService.updatePet(pet, petId);
	// 		return new ResponseEntity<>(res, HttpStatus.OK);
	// 	}

	// }
    
}
