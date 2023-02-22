/*package com.work.LeoProjectGaming;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.repository.BettingHistoryRepository;
import com.work.LeoProjectGaming.service.GamingService;
import com.work.LeoProjectGaming.util.RandomUtil;
import java.util.Date;
import java.util.Random;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.kafka.core.KafkaTemplate;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;

import static com.work.LeoProjectGaming.util.Constants.BETTING_TRANSFER_TOPIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
//@RunWith(PowerMockRunner.class)
//todo fix static Mocks
@PrepareForTest(RandomUtil.class)
class GamingServiceTests extends PowerMockTestCase {

    @Mock
    //@InjectMocks
    BettingHistoryRepository bettingHistoryRepository;

    @Mock
   //@InjectMocks
    KafkaTemplate<String, Object> kafkaTemplate;

  //  @Mock
   // RandomUtil randomUtil;

    //@Mock
   // GamingService gamingService = new GamingService(kafkaTemplate, bettingHistoryRepository);

    @InjectMocks
    GamingService gamingService = new GamingService(kafkaTemplate, bettingHistoryRepository);

    @Captor
    ArgumentCaptor<Bet> playerArgumentCaptor;


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

        PowerMockito.mockStatic(RandomUtil.class);

        when(RandomUtil.getRandomBoolean()).thenReturn(false);
        when(RandomUtil.getRandomInteger()).thenReturn(2);

        gamingService.play(dto, bet);

        assertEquals(-100, dto.getWinAmount());
        assertEquals(0, bet.getWinAmount());
    }

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }
}*/
