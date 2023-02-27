package com.work.LeoProjectGaming;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.repository.BettingHistoryRepository;
import com.work.LeoProjectGaming.service.GamingService;
import com.work.LeoProjectGaming.util.RandomUtil;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static com.work.LeoProjectGaming.TestConstants.BETTING_TRANSFER_DTO;
import static com.work.LeoProjectGaming.util.Constants.BETTING_TRANSFER_TOPIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GamingServiceTests {

    @Mock
    BettingHistoryRepository bettingHistoryRepository;

    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    GamingService gamingService;

    @Captor
    ArgumentCaptor<Bet> playerArgumentCaptor;

    @Test
    void should_send_topic_and_save_bet() {
        when(kafkaTemplate.send(BETTING_TRANSFER_TOPIC, String.valueOf(BETTING_TRANSFER_DTO.getPlayerId()), BETTING_TRANSFER_DTO)).thenReturn(null);

        gamingService.bet(BETTING_TRANSFER_DTO);

        verify(kafkaTemplate, times(1)).send(BETTING_TRANSFER_TOPIC, String.valueOf(BETTING_TRANSFER_DTO.getPlayerId()), BETTING_TRANSFER_DTO);
        verify(bettingHistoryRepository, times(1)).save(playerArgumentCaptor.capture());
        assertEquals(1, playerArgumentCaptor.getValue().getPlayerId());
        assertEquals(100, playerArgumentCaptor.getValue().getBetAmount());
    }

    @Test
    void test_play_method_should_return_right_amount() {
        Bet bet = new Bet(BETTING_TRANSFER_DTO.getPlayerId(), BETTING_TRANSFER_DTO.getBetAmount(), new Date());

        mockStatic(RandomUtil.class);
        Mockito.mock(RandomUtil.class);
        when(RandomUtil.getRandomBoolean()).thenReturn(false);
        when(RandomUtil.getRandomInteger()).thenReturn(2);

        gamingService.play(BETTING_TRANSFER_DTO, bet);

        assertEquals(-100, BETTING_TRANSFER_DTO.getWinAmount());
        assertEquals(0, bet.getWinAmount());
    }
}
