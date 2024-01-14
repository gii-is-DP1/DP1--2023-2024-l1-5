package org.springframework.samples.petclinic.friendship;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.FriendshipExistsException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.State;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FriendshipService {
    FriendshipRepository friendshipRepository;
    PlayerService playerService;
    GameService gameService;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, PlayerService playerService, GameService gameService){
        this.friendshipRepository = friendshipRepository;
        this.playerService = playerService;
        this.gameService = gameService;
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

        List<Player> friendsList = new ArrayList<>();
        for (int id : playerIds) {
            if (id != playerId) { // Excluir el playerId
                Player player = playerService.getPlayerById(id).get();
                if (player != null && state.equals("ALL")) { //Amigos del playerId con cualquier estado
                    friendsList.add(player);
                } else if (player != null && state.equals("PLAYING")) { //Amigos del playerId que esten jugando
                    List<Game> games = gameService.getPlayerGamesInProgress(id);    
                    if (!games.isEmpty() && !friendsList.contains(player)) {
                        friendsList.add(player);
                    }
                } else if (player != null && state.equals("ACTIVE")){
                    if(player.getState().equals(State.ACTIVE) && !friendsList.contains(player)){
                        friendsList.add(player);
                    }
                }
            }
        }

        return friendsList;
    }
    @Transactional(readOnly = true)
    public List<Player> getFriends2(Integer playerId, String state){
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

        List<Player> playerDetails2 = new ArrayList<>();
        for (int id : playerIds) {
            if (id != playerId) { // Excluir el playerId
                Player player = playerService.getPlayerById(id).get();
                if (player != null && state.equals("ALL")) { //Amigos del playerId con cualquier estado
                    playerDetails2.add(player);
                } else if (player != null && state.equals("NOTPLAYING")) { //Amigos del playerId que no esten jugando
                    List<Game> games = gameService.getPlayerGamesInProgress(id); 
                    List<Game> games2 = gameService.getPlayerGamesWaiting(id); 
                    if (games.isEmpty() && games2.isEmpty() && !playerDetails2.contains(player)) {
                        playerDetails2.add(player);
                    }         
                }
            }
        }

        return playerDetails2;
    }
}
