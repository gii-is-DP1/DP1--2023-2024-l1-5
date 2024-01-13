package org.springframework.samples.petclinic.game;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameValidator implements Validator{

    @Override
    public void validate(Object obj, Errors errors) {
        Game game = (Game) obj;
        Integer winner = game.getWinner();
        GameStatus status = game.getStatus();

        if( status != GameStatus.FINALIZED && winner != null){
            errors.rejectValue("winner", "not.allowed", "Winner should be null when the game is not finalized");
        }

        if (game.getGameMode() == null) {
            errors.rejectValue("gameMode", "required", "Game mode is required");
        }

        // Validar el número de jugadores si es aplicable
        if (game.getNumPlayers() != game.getPlayers().size()) {
            errors.rejectValue("numPlayers", "invalid.range", "Number of players should be more than 0");
        }

        if(game.getCreator() == null ){
            errors.rejectValue("creator", "required", "Creator is required");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Game.class.isAssignableFrom(clazz);
    }
    
}
