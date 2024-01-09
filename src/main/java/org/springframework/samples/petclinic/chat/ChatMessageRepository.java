package org.springframework.samples.petclinic.chat;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {

    List<ChatMessage> findAll();

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.game.id = :gameId ORDER BY cm.message_date DESC")
    List<ChatMessage> findChatMessagesByGameId(@Param("gameId") Integer id) throws DataAccessException;
    
}
