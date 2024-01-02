package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.game.exceptions.ActiveGameException;
import org.springframework.samples.petclinic.game.exceptions.WaitingGamesNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GameService {

    PlayerRepository playerRepository;
    GameRepository gameRepository;
    UserService userService;
    PlayerService playerService;

	@Autowired
	public GameService(GameRepository gameRepository,PlayerRepository playerRepository, UserService userService, 
                        PlayerService playerService) {
		this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.userService = userService;
        this.playerService = playerService;
    }

	@Transactional
    public Game saveGame(Game game,Player player) {
        User user = userService.findCurrentUser();
        Player p = playerService.findPlayerByUser(user);
        boolean hasActiveGame = hasActiveGame(p);
        if(hasActiveGame){
            throw new ActiveGameException("El jugador ya tiene una partida activa");
        }
        return gameRepository.save(game);
    }

    @Transactional
    public Game save(Game game){
        return gameRepository.save(game);
    }

    @Transactional(readOnly=true)
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Game> getWaitingGames(){
        return gameRepository.findWaitingGames();
    }

    @Transactional(readOnly=true)
    public List<Game> getInProgressGames(){
        return gameRepository.findInProgressGames();
    }

    @Transactional
    public List<Game> getFinalizedGames(){
        return gameRepository.findFinalizedGames();
    }

    @Transactional(readOnly=true)
    public Optional<Game> getGameById(Integer id) throws DataAccessException{
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

    @Transactional()
    public Game updateGame(int idPlayer, int idGame){
        Player toAddPlayer = playerRepository.findPlayerById(idPlayer).get();
        Game toUpdate= getGameById(idGame).get();
        toUpdate.getPlayers().add(toAddPlayer);
        Integer num  = toUpdate.getNumPlayers();
        num +=1;
        toUpdate.setNumPlayers(num);
        User user = userService.findCurrentUser();
        Player p = playerService.findPlayerByUser(user);
        return saveGame(toUpdate,p);
    } 

    @Transactional()
    public Game updateWinner(Integer idPlayer, Game g){
        Game toSave = new Game();
        BeanUtils.copyProperties(g, toSave,"id");
        toSave.setWinner(idPlayer);
        return save(toSave);
    }

    @Transactional(readOnly=true)
    public Optional<Game> getRandomGame(String gameMode){
        List<Game> games = new ArrayList<>();
        Optional<Game> result = null;
        if(gameMode.equals("QUICK_PLAY")){
            games = gameRepository.findWaitingQuickGames();
        }else if(gameMode.equals("COMPETITIVE")){
            List<Game> ls = gameRepository.findWaitingCompetitiveGames();
            games.addAll(ls);
        }else{
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
        }
        if(games.size() > 0){
            result = Optional.of(games.get(0));
        }
        else{
            throw new WaitingGamesNotFoundException("No se ha encontrado ninguna partida en espera");
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

    @Transactional
    public void deletePlayerFromGame(Integer gameId, Integer currentUserId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        Integer numPl = game.getNumPlayers();
        numPl-=1;
        game.setNumPlayers(numPl);

        List<Player> players = game.getPlayers();
        players.removeIf(p -> p.getUser().getId().equals(currentUserId));
        game.setPlayers(players);

        gameRepository.save(game);
    }
}
    