package org.springframework.samples.petclinic.achievement;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(value = AchievementController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class AchievementControllerTest {

    private static final String BASE_URL = "/api/v1/achievements";
    private static final Integer TEST_ACHIEVEMENT_ID_TESTING = 1;
    private static final Integer TEST_ACHIEVEMENT_ID_PRUEBAS = 2;
    private static final Integer PLAYER_ID = 1;
    private Achievement testing;
    private Achievement pruebas;

    @MockBean
    AchievementService achievementService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        pruebas = new Achievement();
        pruebas.setId(TEST_ACHIEVEMENT_ID_PRUEBAS);
        pruebas.setName("Professional");
        pruebas.setDescription("Win 5 games");
        pruebas.setImageUrl("https://i.imgur.com/0Q0M2YV.png");
        pruebas.setThreshold(5);
        pruebas.setMetric(Metric.VICTORIES);

        testing = new Achievement();
        testing.setId(TEST_ACHIEVEMENT_ID_TESTING);
        testing.setName("New hobby");
        testing.setDescription("Play a total of 3 hours");
        testing.setImageUrl("https://i.imgur.com/0Q0M2YV.png");
        testing.setThreshold(3);
        testing.setMetric(Metric.TOTAL_PLAY_TIME);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetAllAchievements() throws Exception {

        when(this.achievementService.getAchievements()).thenReturn(java.util.List.of(testing, pruebas));

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
	@WithMockUser(value = "admin", authorities = { "ADMIN" })
	void adminShouldFindAchievement() throws Exception {

        Achievement testing = new Achievement();
        testing.setId(TEST_ACHIEVEMENT_ID_TESTING);
        testing.setName("New hobby");
        testing.setMetric(Metric.TOTAL_PLAY_TIME);

		when(this.achievementService.getAchievementById(TEST_ACHIEVEMENT_ID_TESTING)).thenReturn(testing);

		mockMvc.perform(get(BASE_URL + "/{id}", TEST_ACHIEVEMENT_ID_TESTING)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(TEST_ACHIEVEMENT_ID_TESTING))
                .andExpect(jsonPath("$.name").value("New hobby"))
				.andExpect(jsonPath("$.metric").value("TOTAL_PLAY_TIME"));
	}

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetUnlockedAchievementsByPlayerId() throws Exception {
        List<Achievement> mockAchievements = new ArrayList<>();
        when(achievementService.getUnlockedAchievementsByPlayerId(PLAYER_ID)).thenReturn(mockAchievements);

        mockMvc.perform(get(BASE_URL + "/unlocked/" + PLAYER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetLockedAchievementsByPlayerId() throws Exception {
        List<Achievement> mockAchievements = new ArrayList<>();
        when(achievementService.getLockedAchievementsByPlayerId(PLAYER_ID)).thenReturn(mockAchievements);

        mockMvc.perform(get(BASE_URL + "/locked/" + PLAYER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

}
