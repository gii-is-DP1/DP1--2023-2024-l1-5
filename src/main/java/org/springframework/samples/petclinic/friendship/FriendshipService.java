package org.springframework.samples.petclinic.friendship;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FriendshipService {
    FriendshipRepository friendshipRepository;
    PlayerRepository playerRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, PlayerRepository playerRepository){
        this.friendshipRepository = friendshipRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Friendship saveFriendship(Friendship friendship){
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
    public List<Player> getFriends(Integer playerId){
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
                if (player != null) {
                    playerDetails.add(player);
                }
            }
        }

        return playerDetails;
    }

    @Transactional(readOnly = true)
    public boolean checkIfInvitationExists(Integer playerId, Integer requestedPlayerId){
        List<Friendship> friendships = friendshipRepository.findFriendshipRequestByPlayerId(playerId);
        for (Friendship friendship : friendships) {
            if (friendship.getUser_source().getId() == requestedPlayerId || friendship.getUser_dst().getId() == requestedPlayerId) {
                return true;
            }
        }
        return false;
    }
}
