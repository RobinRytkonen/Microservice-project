package com.work.LeoProjectPlayer;

import com.google.gson.Gson;
import com.work.LeoProjectPlayer.entity.PlayerLombok;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.work.LeoProjectPlayer.TestConstants.LIST_OF_PLAYERS;
import static com.work.LeoProjectPlayer.TestConstants.PLAYER_CREATE_DTO;
import static com.work.LeoProjectPlayer.TestConstants.PLAYER_EDIT_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PlayerControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    PlayerRepository playerRepository;

    List<PlayerLombok> currentPlayers;

    @BeforeEach
    void init() {
        currentPlayers = playerRepository.saveAll(LIST_OF_PLAYERS);
    }

    @AfterEach
    void cleanUp() {
        playerRepository.deleteAll();
    }

    @Test
    void should_return_list_of_players() throws Exception {

        Gson gson = new Gson();
        String response = gson.toJson(playerRepository.findAll());

        this.mvc
                .perform(get("/player/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void should_return_player() throws Exception {

        PlayerLombok player =  currentPlayers.get(0);
        Gson gson = new Gson();
        String response = gson.toJson(currentPlayers.get(0));

        this.mvc
                .perform(get("/player/get_player/{id}", player.getPlayerId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void should_save_player() throws Exception {

        Gson gson = new Gson();
        String requestDTO = gson.toJson(PLAYER_CREATE_DTO);

        this.mvc
                .perform(post("/player/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestDTO))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(4, playerRepository.findAll().size());
    }

    @Test
    void should_delete_player() throws Exception {

        PlayerLombok player = currentPlayers.get(0);
        Gson gson = new Gson();
        String requestDTO = gson.toJson(player);

        this.mvc
                .perform(delete("/player/delete").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestDTO))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(2, playerRepository.findAll().size());
    }

    @Test
    void should_edit_player() throws Exception {

        Gson gson = new Gson();
        String requestDTO = gson.toJson(PLAYER_EDIT_DTO);

        this.mvc
                .perform(put("/player/edit_player").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestDTO))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
