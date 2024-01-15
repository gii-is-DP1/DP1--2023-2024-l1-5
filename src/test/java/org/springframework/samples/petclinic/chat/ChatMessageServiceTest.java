package org.springframework.samples.petclinic.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ChatMessageServiceTest {

    private static final Integer TEST_GAME_ID = 1;

    @Autowired
    private ChatMessageService chatMessageService;

    @Test
    public void testFindAll() {
        List<ChatMessage> chatMessages = this.chatMessageService.getChatMessages();

        assertNotNull(chatMessages);
        assertEquals(2, chatMessages.size());
    }

    @Test
    public void testFindChatMessagesByGameId() {
        List<ChatMessage> chatMessages = this.chatMessageService.getChatMessagesByGameId(TEST_GAME_ID);

        assertNotNull(chatMessages);
        assertEquals(2, chatMessages.size());
    }

    @Test
    @Transactional
    public void testSaveChatMessage() {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setContent("Test message");
        newMessage.setSource_user("TestUser");
        newMessage.setGame(null);

        ChatMessage savedMessage = chatMessageService.saveChatMessage(newMessage);

        assertNotNull(savedMessage.getId());
        assertEquals("Test message", savedMessage.getContent());
        assertEquals("TestUser", savedMessage.getSource_user());
    }
}
