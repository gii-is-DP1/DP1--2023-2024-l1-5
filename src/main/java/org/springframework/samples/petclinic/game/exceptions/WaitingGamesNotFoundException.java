package org.springframework.samples.petclinic.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WaitingGamesNotFoundException extends RuntimeException {
    public WaitingGamesNotFoundException(String message) {
        super(message);
    }
}
