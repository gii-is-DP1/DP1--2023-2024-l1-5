package org.springframework.samples.petclinic.hand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hands")
@Tag(name = "Hands", description = "The Hand management API")
@SecurityRequirement(name = "bearerAuth")
public class HandController {

    private final HandService handService;

    @Autowired
    public HandController(HandService handService) {
        this.handService = handService;
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
    
}
