package com.work.LeoProjectPlayer;

import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml")
@ActiveProfiles("test")
@SqlGroup({
        @Sql(value = "classpath:reset-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:initialize-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
@AutoConfigureMockMvc
class PlayerControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    PlayerRepository playerRepository;

    @Test
    void should_return_empty_list() throws Exception {

        String response = "[{\"playerId\":1,\"name\":\"Robin\",\"lastName\":\"RytkÃ¶nen\",\"email\":\"Bobzoor@gmail.com\",\"phoneNumber\":988765,\"location\":\"Vetlanda\",\"country\":\"Sweden\",\"balance\":0.0}]";

        this.mvc
                .perform(get("/player/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void should_return_player() throws Exception {

        String response = "{\"playerId\":1,\"name\":\"Robin\",\"lastName\":\"RytkÃ¶nen\",\"email\":\"Bobzoor@gmail.com\",\"phoneNumber\":988765,\"location\":\"Vetlanda\",\"country\":\"Sweden\",\"balance\":0.0}";

        this.mvc
                .perform(get("/player/get_player/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void should_save_player() throws Exception {

        String player = "{\"name\":\"Robin2\",\"lastName\":\"Rytkönen\",\"email\":\"bobzoor@gmail.com2\",\"phoneNumber\":\"123456789\",\"location\":\"Vetlanda\",\"country\":\"Sweden\",\"balance\":0}";

        this.mvc
                .perform(post("/player/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(player))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(2, playerRepository.findAll().size());
    }

    @Test
    void should_delete_player() throws Exception {

        String response = "{\"playerId\":1,\"name\":\"Robin\",\"lastName\":\"RytkÃ¶nen\",\"email\":\"Bobzoor@gmail.com\",\"phoneNumber\":988765,\"location\":\"Vetlanda\",\"country\":\"Sweden\",\"balance\":0.0}";

        this.mvc
                .perform(delete("/player/delete").contentType(MediaType.APPLICATION_JSON_VALUE).content(response))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(0, playerRepository.findAll().size());
    }

    @Test
    void should_edit_player() throws Exception {

        String edit = "{\"playerId\":1,\"name\":\"Robin2\",\"lastName\":\"RytkÃ¶nen2\",\"email\":\"Bobzoor@gmail.com\",\"phoneNumber\":988765,\"location\":\"Vetlanda\",\"country\":\"Sweden\",\"balance\":0.0}";

        this.mvc
                .perform(put("/player/edit_player").contentType(MediaType.APPLICATION_JSON_VALUE).content(edit))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
