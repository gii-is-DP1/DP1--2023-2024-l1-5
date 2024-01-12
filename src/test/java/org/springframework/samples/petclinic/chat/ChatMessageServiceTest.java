package org.springframework.samples.petclinic.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatMessageServiceTest {

    private static final Integer TEST_GAME_ID = 1;
    private ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageServiceTest(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @Test
    void testFindAll() {
        List<ChatMessage> chatMessages = this.chatMessageService.getChatMessages();

        assertNotNull(chatMessages);
        assertEquals(2, chatMessages.size());
    }

    @Test
    void testFindChatMessagesByGameId() {
        List<ChatMessage> chatMessages = this.chatMessageService.getChatMessagesByGameId(TEST_GAME_ID);

        assertNotNull(chatMessages);
        assertEquals(2, chatMessages.size());
    }

    @Test
    @Transactional
    void testSaveChatMessage() {
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