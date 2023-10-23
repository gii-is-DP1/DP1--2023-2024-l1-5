package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.exceptions.WaitingGamesNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    GameRepository gameRepository;
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	@Transactional
    public Game saveGame(Game game) {
        gameRepository.save(game);
        return game;
    }
    @Transactional(readOnly=true)
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }
    @Transactional(readOnly=true)
    public Optional<Game> getGameById(Integer id){
        return gameRepository.findById(id);
    }
    @Transactional(readOnly=true)
    public Boolean hasActiveGame(Player player){
        List<Game> playerGames = gameRepository.findPlayerCreatedGames(player.getId());
        Boolean result = false;
        for(Game game: playerGames){
            result = result || game.getStatus().equals(GameStatus.WAITING) || game.getStatus().equals(GameStatus.IN_PROGRESS);
        }
        return result;
    }

    @Transactional(readOnly=true)
    public Optional<Game> getRandomGame(String gameMode){
        List<Game> games = null;
        Optional<Game> result = null;
        if(gameMode.equals("QUICK_START")){
            games = gameRepository.findWaitingQuickGames();
        }else if(gameMode.equals("COMPETITIVE")){
            games = gameRepository.findWaitingCompetitiveGames();
        }else{
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
        }
        if(games.size() > 0){
            result = Optional.of(games.get(0));
        }
        return result;
    }

    @Transactional(readOnly=true)
    public Optional<Game> getWaitingGame(Player player){
        List<Game> playerGames = gameRepository.findPlayerCreatedGames(player.getId());
        Optional<Game> result = null;
        for(Game game: playerGames){
            if(game.getStatus().equals(GameStatus.WAITING)){
                result = Optional.of(game);
                break;
            }
        }
        return result;
    }
}
