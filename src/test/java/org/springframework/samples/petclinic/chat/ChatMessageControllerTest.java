package org.springframework.samples.petclinic.chat;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatMessageControllerTest {

    private static final String BASE_URL = "/api/v1/chatMessages";
    private static final Integer GAME_ID = 2;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Mock
    private ChatMessageService chatMessageService;

    @InjectMocks
    private ChatMessageController chatMessageController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "player2", authorities = {"PLAYER"})
    public void testGetAllChatMessages() throws Exception {
        List<ChatMessage> mockMessages = new ArrayList<>();
        when(chatMessageService.getChatMessages()).thenReturn(mockMessages);

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.size()").value(2));;
    }

    @Test
    @WithMockUser(username = "player2", authorities = {"PLAYER"})
    public void testGetChatMessagesByGameId() throws Exception {
        List<ChatMessage> mockMessages = new ArrayList<>();
        when(chatMessageService.getChatMessagesByGameId(GAME_ID)).thenReturn(mockMessages);

        mockMvc.perform(get(BASE_URL + "/{gameId}", GAME_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.size()").value(2));
    }

    // Aquí puedes agregar más tests para otros métodos en tu ChatMessageController
    // ...
}
