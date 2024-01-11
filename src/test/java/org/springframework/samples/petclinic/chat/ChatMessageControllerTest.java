package org.springframework.samples.petclinic.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.State;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ChatMessageController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ChatMessageControllerTest {

    private static final String BASE_URL = "/api/v1/chatMessages";
    private static final int TEST_CHAT_MESSAGE_ID = 1;
    private static final int TEST_GAME_ID = 1;
    private static final int TEST_PLAYER_ID = 1;
    private static final int TEST_USER_ID = 1;

    @MockBean
    private GameService gameService;

    @MockBean
    private ChatMessageService chatMessageService;

    @MockBean
    private UserService userService;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    private Game game;
    private User user;
    private Player player;
    private ChatMessage chatMessage;

    @BeforeEach
    void setup() {

        player = new Player();
        player.setId(TEST_PLAYER_ID);
        player.setFirstName("Lucas");
        player.setLastName("Antonanzas");
        player.setImage("image");
        player.setState(State.ACTIVE);
        user = new User();
        user.setId(TEST_USER_ID);
        user.setUsername("player");
        user.setPassword("player");
        player.setUser(user);

        game = new Game();
        game.setId(TEST_GAME_ID);

        chatMessage = new ChatMessage();
        chatMessage.setId(TEST_CHAT_MESSAGE_ID);
        chatMessage.setGame(game);
        chatMessage.setSource_user(player.getPlayerUsername());
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
