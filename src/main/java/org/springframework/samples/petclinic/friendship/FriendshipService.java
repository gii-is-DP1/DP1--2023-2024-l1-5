package org.springframework.samples.petclinic.friendship;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FriendshipService {
    FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository){
        this.friendshipRepository = friendshipRepository;
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
    public List<Friendship> getAcceptedFriendshipsByPlayerId(Integer playerId){
        return friendshipRepository.findAcceptedFriendshipsByPlayerId(playerId);
    }
}
