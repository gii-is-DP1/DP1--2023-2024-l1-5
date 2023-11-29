package org.springframework.samples.petclinic.friendship;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.FriendshipExistsException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.game.GameStatus;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FriendshipService {
    FriendshipRepository friendshipRepository;
    PlayerRepository playerRepository;
    GameRepository gameRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, PlayerRepository playerRepository, GameRepository gameRepository){
        this.friendshipRepository = friendshipRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional(rollbackFor = {FriendshipExistsException.class})
    public Friendship saveFriendship(Friendship friendship, String type){
        Player playerSource = friendship.getUser_source();
        Player playerDst = friendship.getUser_dst();
        List<Friendship> friendships = friendshipRepository.findFriendshipRequestByPlayerId(playerSource.getId());
        if(type=="POST"){
            for (Friendship f : friendships) {
                if (f.getUser_source().getId() == playerDst.getId() ||  f.getUser_dst().getId() == playerDst.getId()) {
                    throw new FriendshipExistsException("Friendship already exists between these two players");
                }
            }
        }
        friendshipRepository.save(friendship);
        return friendship;
    }

    @Transactional(readOnly = true)
    public List<Friendship> getAllFriendships(){
        return friendshipRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Friendship getFriendshipById(Integer friendshipId){
        return friendshipRepository.findById(friendshipId).get();
    }
    @Transactional(readOnly = true)
    public List<Friendship> getFriendshipRequests(Integer playerId){
        return friendshipRepository.findFriendshipRequests(playerId);
    }

    @Transactional(readOnly = true)
    public List<Friendship> getAcceptedFriendshipsByPlayerId(Integer playerId){
        return friendshipRepository.findAcceptedFriendshipsByPlayerId(playerId);
    }

    @Transactional(readOnly = true)
    public List<Player> getFriends(Integer playerId, String state){
        List<Friendship> friendships = friendshipRepository.findAcceptedFriendshipsByPlayerId(playerId);
        Set<Integer> playerIds = new HashSet<>();

        for (Friendship friendship : friendships) {
            if (friendship.getUser_source().getId() != playerId) {
                playerIds.add(friendship.getUser_source().getId());
            }
            if (friendship.getUser_dst().getId() != playerId) {
                playerIds.add(friendship.getUser_dst().getId());
            }
        }

        List<Player> playerDetails = new ArrayList<>();
        for (int id : playerIds) {
            if (id != playerId) { // Excluir el playerId
                Player player = playerRepository.findPlayerById(id).get();
                if (player != null && state.equals("ALL")) { //Amigos del playerId con cualquier estado
                    playerDetails.add(player);
                } else if (player != null && state.equals("PLAYING")) { //Amigos del playerId que esten jugando
                    List<Game> games = gameRepository.findPlayerGamesInProgress(id);
                    if (!games.isEmpty() && !playerDetails.contains(player)) {
                        playerDetails.add(player);
                    }
                    
                }
            }
        }

        return playerDetails;
    }

}
