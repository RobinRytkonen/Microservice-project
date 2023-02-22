package com.work.LeoProjectGaming;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.service.GamingService;
import java.util.Date;
import org.aspectj.lang.annotation.Before;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GamingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GamingService gamingService;

    @Test
    void bet_test() throws Exception {

        BettingTransferDTO dto = new BettingTransferDTO(1, 100);
        Bet bet = new Bet(dto.getPlayerId(), dto.getBetAmount(), new Date());
        when(gamingService.bet(dto)).thenReturn(bet);

        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());


        verify(gamingService, times(1)).bet(dto);



    }
}
