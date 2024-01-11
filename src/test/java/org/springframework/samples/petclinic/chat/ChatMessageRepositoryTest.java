package org.springframework.samples.petclinic.chat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest
public class ChatMessageRepositoryTest {

    private static final Integer GAME_ID = 1; 

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Test
    public void testFindAll() {
        List<ChatMessage> chatMessages = chatMessageRepository.findAll();
        assertNotNull(chatMessages);
        assertTrue(chatMessages.size() > 0);
    }

    @Test
    public void testFindChatMessagesByGameId() {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByGameId(GAME_ID);
        assertNotNull(chatMessages); 
        assertFalse(chatMessages.isEmpty()); 
        assertTrue(chatMessages.stream().anyMatch(message -> "Que tal? Eres bueno?".equals(message.getContent())));
    }

}