package life.majd.tictactoe.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.domain.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PlayerService playerService;

    @Test
    void whenSavePlayer_ThenReturn201AndPlayer() throws Exception {

        Player player = Player.builder().id(1L).name("Bob").build();

        when(playerService.createPlayer(any())).thenReturn(player);

        MvcResult mvcResult = mockMvc.perform(post("http://localhost:8080/v1/player")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(player));
    }


}