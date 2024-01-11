package org.springframework.samples.petclinic.chat;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ChatMessageController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ChatMessageControllerTest {

    private static final String BASE_URL = "/api/v1/chatMessages";
    private static final int TEST_CHAT_MESSAGE_ID = 1;
    private static final int TEST_GAME_ID = 1;

    @MockBean
    private GameService gameService;

    @MockBean
    private ChatMessageService chatMessageService;

    @Autowired
    private MockMvc mockMvc;

    private Game game;
    private ChatMessage chatMessage;

    @BeforeEach
    void setup() {

        game = new Game();
        game.setId(TEST_GAME_ID);

        chatMessage = new ChatMessage();
        chatMessage.setId(TEST_CHAT_MESSAGE_ID);
        chatMessage.setGame(game);
        chatMessage.setSource_user("player");
        chatMessage.setContent("message");
        chatMessage.setMessage_date(LocalDateTime.now());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void shouldFindAllChatMessages() throws Exception {

        when(this.chatMessageService.getChatMessages()).thenReturn(List.of(chatMessage));

        mockMvc.perform(get(BASE_URL))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1))
               .andExpect(jsonPath("$[0].content").value("message"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void shouldFindChatMessagesByGameId() throws Exception {

        when(this.chatMessageService.getChatMessagesByGameId(TEST_GAME_ID)).thenReturn(List.of(chatMessage));

        mockMvc.perform(get(BASE_URL + "/{gameId}", TEST_GAME_ID))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1))
               .andExpect(jsonPath("$[0].id").value(TEST_CHAT_MESSAGE_ID));
    }
}
