package org.springframework.samples.petclinic.round.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WaitingGameException extends RuntimeException {
    public WaitingGameException(String message) {
        super(message);
    }
}
