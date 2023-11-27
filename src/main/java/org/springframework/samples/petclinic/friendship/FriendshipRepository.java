package org.springframework.samples.petclinic.friendship;



import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer>{

    List<Friendship> findAll() throws DataAccessException;
    
    @Query("SELECT f FROM Friendship f WHERE (f.user_dst.id = :playerId OR f.user_source.id = :playerId) AND f.status = 'ACCEPTED'")
    List<Friendship> findAcceptedFriendshipsByPlayerId(Integer playerId) throws DataAccessException;
    
    @Query("SELECT DISTINCT CASE WHEN f.user_source.id = :playerId THEN f.user_dst ELSE f.user_source END " +
        "FROM Friendship f WHERE (f.user_dst.id = :playerId OR f.user_source.id = :playerId) AND f.status = 'ACCEPTED'")
    List<Player> findFriendsByPlayerId(Integer playerId);

}
