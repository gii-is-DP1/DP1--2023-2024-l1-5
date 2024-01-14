package org.springframework.samples.petclinic.game;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameValidator implements Validator{

    @Override
    public void validate(Object obj, Errors errors) {
        if(obj instanceof Game){
            this.validateGame((Game) obj, errors);
        } else if(obj instanceof GameRequest){
            this.validateGameRequest((GameRequest) obj, errors);
        } else if(obj instanceof GameRequestPUT){
            this.validateGameRequestPUT((GameRequestPUT) obj, errors);
        } else if(obj instanceof Integer){
            this.validateId((Integer) obj, errors);
        }
        else{
            throw new IllegalArgumentException("The class "+obj.getClass().getName()+" is not supported by this validator");
        }
    }

    public void validateGame(Game game, Errors errors) {
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

    public void validateGameRequest(GameRequest gameRequest, Errors errors) {

        if (gameRequest.getGameMode() != GameMode.QUICK_PLAY && gameRequest.getGameMode() != GameMode.COMPETITIVE) {
            errors.rejectValue("gameMode", "required", "Game mode is required");
        }
    }

    public void validateGameRequestPUT(GameRequestPUT gameRequestPUT, Errors errors) {

        Integer winner = gameRequestPUT.getWinner();
        GameStatus status = gameRequestPUT.getStatus();

        if( status != GameStatus.FINALIZED && winner != null){
            errors.rejectValue("winner", "not.allowed", "Winner should be null when the game is not finalized");
        }

        if (gameRequestPUT.getPlayers().size()<1) {
            errors.rejectValue("numPlayers", "invalid.range", "Number of players should be more than 0");
        }
    }

    public void validateId(Integer id, Errors errors) {
        if (id == null || id <= 0) {
            errors.rejectValue("id", "invalid.id", "ID should be a positive integer");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Game.class.isAssignableFrom(clazz) || GameRequest.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz) || GameRequestPUT.class.isAssignableFrom(clazz);
    }
    
}
