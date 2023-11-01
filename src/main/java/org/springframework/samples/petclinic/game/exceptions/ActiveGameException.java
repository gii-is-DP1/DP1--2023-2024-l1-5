package org.springframework.samples.petclinic.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ActiveGameException extends RuntimeException {
    public ActiveGameException(String message) {
        super(message);
    }
}
