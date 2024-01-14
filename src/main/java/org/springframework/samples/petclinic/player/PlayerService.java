package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.PlayerNotFoundException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.friendship.Friendship;
import org.springframework.samples.petclinic.friendship.FriendshipRepository;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameInfo;
import org.springframework.samples.petclinic.game.GameInfoRepository;
import org.springframework.samples.petclinic.invitation.Invitation;
import org.springframework.samples.petclinic.invitation.InvitationService;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;

@Service
public class PlayerService {

    PlayerRepository playerRepository;

    FriendshipRepository friendshipRepository;

    GameRepository gameRepository;

    GameInfoRepository gameInfoRepository;

    UserService userService;

    InvitationService invitationService;

    HandRepository handRepository;
    
    
    @Autowired
	public PlayerService(PlayerRepository playerRepository, FriendshipRepository friendshipRepository, GameRepository gameRepository, UserService userService, GameInfoRepository gameInfoRepository,InvitationService invitationService,HandRepository handRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
		this.playerRepository = playerRepository;
        this.friendshipRepository = friendshipRepository;
        this.gameInfoRepository = gameInfoRepository;
        this.invitationService = invitationService;
        this.handRepository = handRepository;
	}
	@Transactional
    public Player savePlayer(Player player) {
        playerRepository.save(player);
        return player;
    }
    @Transactional(readOnly=true)
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }
    @Transactional(readOnly=true)
    public Optional<Player> getPlayerById(Integer id){
        return playerRepository.findById(id);
    }
    @Transactional(readOnly=true)
    public Player findPlayerByUser(User user) throws DataAccessException{
        return playerRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Player", "ID", user.getId()));
    }
    @Transactional(readOnly=true)
    public Player getPlayerByUserId(Integer id) throws DataAccessException{
        return playerRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("Player", "ID", id));
    }
    @Transactional(readOnly=true)
    public Player getPlayerByUsername(String username) throws DataAccessException{
        return playerRepository.findByUsername(username).orElseThrow(() -> new PlayerNotFoundException("User with username " + username +" not found"));
    }

    @Transactional
	public Player updatePlayer(Player player, int id) throws DataAccessException {
		Player toUpdate = getPlayerById(id).orElse(null);
		BeanUtils.copyProperties(player, toUpdate, "id", "user");
		playerRepository.save(toUpdate);
		return toUpdate;
	}

    @Transactional
    public Boolean existsPlayerUser(String playerUsername) {
		return playerRepository.existsByPlayerUsername(playerUsername);
    }

    @Transactional
	public void deleteGame(int id) throws DataAccessException {
        List<Invitation> allInvitations = invitationService.getAllInvitationsByGameId(id);
        for (Invitation i: allInvitations){
            invitationService.deleteInvitation(i);
        }
        GameInfo gameInf = gameInfoRepository.findByGameId(id);
        gameInfoRepository.delete(gameInf);		
        Game toDelete = gameRepository.findById(id).orElse(null);
		gameRepository.delete(toDelete);
	}

    @Transactional
    public void deletePlayerFromGame(Integer gameId, Integer currentUserId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        GameInfo gameInfo = gameInfoRepository.findByGameId(gameId);
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
                gameInfo.setCreator(newCreator);

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
	public void deletePlayer(int id) throws DataAccessException {
		Player toDelete = getPlayerById(id).get();
        Integer userId = toDelete.getUser().getId();
        List<Friendship> friendships = friendshipRepository.findFriendshipRequestByPlayerId(toDelete.getId());
        for (Friendship friendship : friendships) {
            friendshipRepository.delete(friendship);
        }
        List<Game> playerGames = gameRepository.findGamesByPlayerIdAll(id);
        for(Game g : playerGames){
            deletePlayerFromGame(g.getId(), userId);
        }
        Hand playerHand = handRepository.findHandByPlayerId(id);
        if (playerHand != null){
            handRepository.delete(playerHand);
        }
		playerRepository.delete(toDelete);
        userService.deleteByUserId(userId);
	}
}
