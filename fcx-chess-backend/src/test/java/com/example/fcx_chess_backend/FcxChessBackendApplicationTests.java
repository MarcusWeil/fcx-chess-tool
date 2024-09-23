package com.example.fcx_chess_backend;

import com.example.fcx_chess_backend.fcxMainController.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class FcxMainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private Tournament sampleTournament;

    @BeforeEach
    void setup() {
        sampleTournament = new Tournament(3, "Spring Tournament", "2024-10-20");
    }

    @Test
    void shouldFetchTournaments() throws Exception {
        mockMvc.perform(get("/api/tournaments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Summer Championship")))
                .andExpect(content().string(containsString("Winter Classic")));
    }

    @Test
    void shouldFetchPlayers() throws Exception {
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("John Doe")))
                .andExpect(content().string(containsString("Jane Smith")));
    }

    @Test
    void shouldRegisterTournament() throws Exception {
        String newTournamentJson = """
        {
            "id": 3,
            "name": "Spring Tournament",
            "date": "2024-10-20"
        }
        """;

        mockMvc.perform(post("/api/tournaments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTournamentJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tournament registered: Spring Tournament")));
    }

    @Test
    void shouldSubmitResults() throws Exception {
        String results = "Player 1 won against Player 2";

        mockMvc.perform(post("/api/tournament/results")
                .contentType(MediaType.TEXT_PLAIN)
                .content(results))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Results submitted: Player 1 won against Player 2")));
    }
}
