package org.springframework.samples.petclinic.configuration;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.game.exceptions.ActiveGameException;
import org.springframework.samples.petclinic.round.exceptions.WaitingGameException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration 
{
	@SuppressWarnings("unused")
	@Autowired
	private BasicErrorController errorController;
    // add any exceptions/validations/binding problems

   @ExceptionHandler(Exception.class)
   public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {
        request.setAttribute("jakarta.servlet.error.request_uri", request.getPathInfo());
        request.setAttribute("jakarta.servlet.error.status_code", 400);
        request.setAttribute("exeption", ex);
        return "exception";
    }
    //EXCEPTION HANDLER FOR ACTIVE GAME EXCEPTION
    @ExceptionHandler(ActiveGameException.class)
    public ResponseEntity<String> handleActiveGameException(ActiveGameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(WaitingGameException.class)
    public ResponseEntity<String> handleWaitingGameException(WaitingGameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}