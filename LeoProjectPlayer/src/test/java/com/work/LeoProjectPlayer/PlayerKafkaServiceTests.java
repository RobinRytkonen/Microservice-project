package com.work.LeoProjectPlayer;

import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import com.work.LeoProjectPlayer.service.PlayerKafkaService;
import java.util.Optional;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerKafkaServiceTests {

    Player player1 = new Player("Robin", "Rytkönen", "Bobzoor@gmail.com", 988765, "Vetlanda", "Sweden", 0);
    BettingTransferDTO  dto = new BettingTransferDTO(1, 100, 1000);
    TransactionDTO transactionDTO = new TransactionDTO(1,"deposit", 50000);

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerKafkaService playerKafkaService;

    @Captor
    ArgumentCaptor<Player> playerArgumentCaptor;

    @Test
    void no_player_found_should_return_null() {

        when(playerRepository.findById(anyInt())).thenReturn(Optional.empty());

        var player2 = playerKafkaService.paymentTransaction(transactionDTO);
        var player3 = playerKafkaService.bettingMoneyTransfer(dto);

        Assertions.assertNull(player2);
        Assertions.assertNull(player3);

    }

    @Test
    void should_update_player_balance_with_bet_win_amount() {

        when(playerRepository.findById(dto.getPlayerId())).thenReturn(Optional.of(player1));

        playerKafkaService.bettingMoneyTransfer(dto);

        verify(playerRepository, times(1)).findById(dto.getPlayerId());
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(1000, playerArgumentCaptor.getValue().getBalance());
    }

    @Test
    void should_update_player_balance_with_deposit() {

        when(playerRepository.findById(transactionDTO.getTransactionId())).thenReturn(Optional.of(player1));

        playerKafkaService.paymentTransaction(transactionDTO);

        verify(playerRepository,times(1)).findById(transactionDTO.getTransactionId());
        verify(playerRepository,times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(50000, playerArgumentCaptor.getValue().getBalance());
    }
}
