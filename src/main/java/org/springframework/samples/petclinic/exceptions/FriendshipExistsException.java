package org.springframework.samples.petclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FriendshipExistsException extends RuntimeException {
    public FriendshipExistsException(String message) {
        super(message);
    }
}