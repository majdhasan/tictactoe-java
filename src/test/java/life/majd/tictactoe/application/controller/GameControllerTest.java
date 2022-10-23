package life.majd.tictactoe.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.exceptions.NoSuchGameException;
import life.majd.tictactoe.domain.exceptions.NoSuchPlayerException;
import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.domain.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GameController.class)
class GameControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    GameService gameService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenStartNewGameSuccessfully_Return201CreatedAndGame() throws Exception {

        Game game = Game.builder().build();
        when(gameService.createGame(any())).thenReturn(game);

        GameRequest gameRequest = GameRequest.builder().player1(1L).player2(2L).build();

        MvcResult mvcResult = mockMvc.perform(post("http://localhost:8080/v1/game")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andExpect(status().isCreated()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(game));

    }

    @Test
    void whenStartNewGameWithWrongPlayerIds_Return406() throws Exception {

        when(gameService.createGame(any())).thenThrow(new NoSuchPlayerException("No such player found"));

        GameRequest gameRequest = GameRequest.builder().player1(111L).player2(222L).build();

        MvcResult mvcResult = mockMvc.perform(post("http://localhost:8080/v1/game")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andExpect(status().isNotAcceptable()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("No such player found");
    }

    @Test
    void whenRequestExistingGame_Return200AndGame() throws Exception {

        Game game = Game.builder().build();
        when(gameService.getGame(any())).thenReturn(game);

        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/v1/game/1"))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(game));
    }

    @Test
    void whenRequestNotFoundGame_Return404() throws Exception {

        Game game = Game.builder().build();
        String exceptionMessage = "No such game found";
        when(gameService.getGame(any())).thenThrow(new NoSuchGameException(exceptionMessage));

        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/v1/game/1"))
                .andExpect(status().isNotAcceptable()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains(exceptionMessage);
    }

}