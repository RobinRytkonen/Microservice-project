package com.work.LeoProjectGaming;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.repository.BettingHistoryRepository;
import com.work.LeoProjectGaming.service.GamingService;
import com.work.LeoProjectGaming.util.RandomUtil;
import java.util.Date;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static com.work.LeoProjectGaming.util.Constants.BETTING_TRANSFER_TOPIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GamingServiceTests {

    BettingHistoryRepository bettingHistoryRepository;
    KafkaTemplate<String, Object> kafkaTemplate;
    GamingService gamingService;

    @Captor
    ArgumentCaptor<Bet> playerArgumentCaptor;

    @BeforeEach
    void init() {
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        bettingHistoryRepository = Mockito.mock(BettingHistoryRepository.class);
        gamingService = new GamingService(kafkaTemplate, bettingHistoryRepository);
    }

    @Test
    void should_save_bet() {
        BettingTransferDTO dto = new BettingTransferDTO(1, 100);
        Bet bet = new Bet(dto.getPlayerId(), dto.getBetAmount(), new Date());

        when(kafkaTemplate.send(BETTING_TRANSFER_TOPIC, String.valueOf(dto.getPlayerId()), dto)).thenReturn(null);
        when(bettingHistoryRepository.save(any(Bet.class))).thenReturn(bet);

        gamingService.bet(dto);

        verify(kafkaTemplate, times(1)).send(BETTING_TRANSFER_TOPIC, String.valueOf(dto.getPlayerId()), dto);
        verify(bettingHistoryRepository, times(1)).save(playerArgumentCaptor.capture());
        assertEquals(1, playerArgumentCaptor.getValue().getPlayerId());
        assertEquals(100, playerArgumentCaptor.getValue().getBetAmount());
    }

    @Test
    void test_play_method_should_return_right_amount() {
        BettingTransferDTO dto = new BettingTransferDTO(1, 100);
        Bet bet = new Bet(dto.getPlayerId(), dto.getBetAmount(), new Date());

        mockStatic(RandomUtil.class);
        var mockRandomInstant = Mockito.mock(RandomUtil.class);
        when(mockRandomInstant.getRandomBoolean()).thenReturn(false);
        when(mockRandomInstant.getRandomInteger()).thenReturn(2);

        gamingService.play(dto, bet);

        assertEquals(-100, dto.getWinAmount());
        assertEquals(0, bet.getWinAmount());
    }
}
