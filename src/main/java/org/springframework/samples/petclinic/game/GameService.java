package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
}
