package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import org.hibernate.internal.util.type.PrimitiveWrapperHelper.IntegerDescriptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.game.exceptions.ActiveGameException;
import org.springframework.samples.petclinic.game.exceptions.WaitingGamesNotFoundException;
import org.springframework.samples.petclinic.invitation.Invitation;
import org.springframework.samples.petclinic.invitation.InvitationService;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GameService {

    GameRepository gameRepository;
    UserService userService;
    PlayerService playerService;
    GameInfoRepository gameInfoRepository;
    InvitationService invitationService;

	@Autowired
	public GameService(GameRepository gameRepository, UserService userService, 
                        PlayerService playerService,GameInfoRepository gameInfoRepository, InvitationService invitationService) {
		this.gameRepository = gameRepository;
        this.userService = userService;
        this.playerService = playerService;
        this.gameInfoRepository = gameInfoRepository;
        this.invitationService = invitationService;
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
        Game wGame = gameRepository.findWaitingPlayerGame(player.getId());
        Game iGame = gameRepository.findInProgressPlayerGame(player.getId());
        Boolean result = true;
        if(wGame == null && iGame == null){
            result = false;
        }
        return result;
    }
    @Transactional(readOnly = true)
    public Game getInProgressGame(Integer playerId){
        return gameRepository.findInProgressPlayerGame(playerId);
    }

    @Transactional(readOnly=true)
    public List<Game> getGameFromPlayer(Player player){
        Game wGame = gameRepository.findWaitingPlayerGame(player.getId());
        Game iGame = gameRepository.findInProgressPlayerGame(player.getId());
        List<Game> result = new ArrayList<>();
        if(wGame != null)
            result.add(wGame);
        if(iGame != null)
            result.add(iGame);
        return result;
    }
    
    @Transactional()
    public Game updateGame(int idPlayer, int idGame){
        Player toAddPlayer = playerService.getPlayerById(idPlayer).get();
        boolean hasActiveGame = hasActiveGame(toAddPlayer);
        if(hasActiveGame){
            throw new ActiveGameException("El jugador ya tiene una partida activa");
        }
        Game toUpdate= getGameById(idGame).get();
        List<Player> players = toUpdate.getPlayers();
        players.add(toAddPlayer);
        toUpdate.setPlayers(players);
        toUpdate.setNumPlayers(players.size());
        Game updatedGame = save(toUpdate);
        return updatedGame;
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
	public void deleteGame(int id) throws DataAccessException {
        List<Invitation> allInvitations = invitationService.getAllInvitationsByGameId(id);
        for (Invitation i: allInvitations){
            invitationService.deleteInvitation(i);
        }
        GameInfo gameInf = gameInfoRepository.findByGameId(id);
        gameInfoRepository.delete(gameInf);		
        Game toDelete = getGameById(id).orElse(null);
		gameRepository.delete(toDelete);
	}

    @Transactional
    public void deletePlayerFromGame(Integer gameId, Integer currentUserId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        Integer numPl = game.getNumPlayers();
        Integer creatorId = game.getCreator().getUser().getId();

        if (numPl == 1){
            deleteGame(gameId);
        }else{
            if(creatorId.equals(currentUserId)){
                numPl-=1;
                game.setNumPlayers(numPl);

                List<Player> players = game.getPlayers();
                players.removeIf(p -> p.getUser().getId().equals(currentUserId));

                Player newCreator = players.get(0);
                game.setCreator(newCreator);
                game.setPlayers(players);

                gameRepository.save(game);
            }else{
                numPl-=1;
                game.setNumPlayers(numPl);

                List<Player> players = game.getPlayers();
                players.removeIf(p -> p.getUser().getId().equals(currentUserId));
                game.setPlayers(players);

                gameRepository.save(game);
            }
        }
    }

    @Transactional
    public List<Game> getPlayerGamesInProgress(Integer id){
        return gameRepository.findPlayerGamesInProgress(id);
    }
    
    @Transactional
    public List<Game> getPlayerGamesWaiting(Integer id){
        return gameRepository.findPlayerGamesWaiting(id);
    }

    @Transactional
    public List<Game> getGamesByPlayerId(Integer id){
        return gameRepository.findGamesByPlayerId(id);
    }

    @Transactional
    public Integer getNumGamesByPlayerId(Integer id){
        return gameRepository.findNumGamesByPlayerId(id);
    }

    @Transactional
    public Integer getNumGamesWinByPlayerId(Integer id){
        return gameRepository.findNumGamesWinByPlayerId(id);
    }

    @Transactional
    public Integer getTimesGamesWinByPlayerId(Integer id){
        return gameRepository.findTimeGamesByPlayerId(id);
    }

    @Transactional
    public Integer getMaxTimeGamesByPlayerId(Integer id){
        return gameRepository.findMaxTimeGamesByPlayerId(id);
    }

    @Transactional
    public Integer getMinTimeGamesByPlayerId(Integer id){
        return gameRepository.findMinTimeGamesByPlayerId(id);
    }

    @Transactional
    public Double getAvgTimeGamesByPlayerId(Integer id){
        return gameRepository.findAvgTimeGamesByPlayerId(id);
    }

    @Transactional
    public Map<String, Integer> getRanking() {
        Map<String, Integer> completeRanking = rank();

        Map<String, Integer> top5Ranking = completeRanking.entrySet()
                .stream()
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return top5Ranking;
    }

    public Map<String, Integer> rank() {
        List<Player> players = playerService.getAllPlayers();

        List<Player> filteredPlayers = players.stream()
                .filter(player -> getNumGamesWinByPlayerId(player.getUser().getId()) > 0)
                .collect(Collectors.toList());

        Map<String, Integer> ranking = filteredPlayers.stream()
                .collect(Collectors.toMap(
                        player -> player.getUser().getUsername(),
                        player -> getNumGamesWinByPlayerId(player.getUser().getId())
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return ranking;
    }

    @Transactional
    public Integer myRank(Integer playerId){
        Map<String, Integer> ranking = rank();
        Map<String, Integer> sortedMap = ranking.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);
        Integer myRank = 0;
        for(Map.Entry<String, Integer> entry : sortedMap.entrySet()){
            myRank++;
            if(entry.getKey().equals(playerService.getPlayerById(playerId).get().getUser().getUsername())){
                return myRank;
            }
        }
        return 0;
    }
}
    