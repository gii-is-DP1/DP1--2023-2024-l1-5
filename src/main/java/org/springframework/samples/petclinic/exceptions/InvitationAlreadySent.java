package org.springframework.samples.petclinic.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvitationAlreadySent  extends RuntimeException{
    public InvitationAlreadySent(String message) {
        super(message);
    }
    
}
