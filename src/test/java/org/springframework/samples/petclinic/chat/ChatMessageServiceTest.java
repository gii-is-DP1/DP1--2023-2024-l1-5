package org.springframework.samples.petclinic.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {

    private static final Integer GAME_ID = 1;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatMessageService chatMessageService;

    @Test
    public void testSaveChatMessage() {
        ChatMessage chatMessageToSave = new ChatMessage();
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessageToSave);

        ChatMessage savedChatMessage = chatMessageService.saveChatMessage(chatMessageToSave);

        assertNotNull(savedChatMessage);
        assertEquals(chatMessageToSave, savedChatMessage);
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    public void testGetAllChatMessages() {
        List<ChatMessage> chatMessages = new ArrayList<>();
        when(chatMessageRepository.findAll()).thenReturn(chatMessages);

        List<ChatMessage> result = chatMessageService.getChatMessages();

        assertNotNull(result);
        assertEquals(chatMessages, result);
        verify(chatMessageRepository, times(1)).findAll();
    }

    @Test
    public void testGetChatMessagesByGameId() {
        List<ChatMessage> mockChatMessages = new ArrayList<>();
        when(chatMessageRepository.findChatMessagesByGameId(GAME_ID)).thenReturn(mockChatMessages);

        List<ChatMessage> result = chatMessageService.getChatMessagesByGameId(GAME_ID);

        assertNotNull(result);
        verify(chatMessageRepository, times(1)).findChatMessagesByGameId(GAME_ID);
    }

}
