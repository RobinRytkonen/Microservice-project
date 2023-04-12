package com.work.LeoProjectPlayer;

import com.work.LeoProjectPlayer.entity.PlayerLombok;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import com.work.LeoProjectPlayer.service.PlayerKafkaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.work.LeoProjectPlayer.TestConstants.BETTING_TRANSFER_DTO;
import static com.work.LeoProjectPlayer.TestConstants.PLAYER;
import static com.work.LeoProjectPlayer.TestConstants.TRANSACTION_DTO;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerKafkaServiceTests {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerKafkaService playerKafkaService;

    @Captor
    ArgumentCaptor<PlayerLombok> playerArgumentCaptor;

    @BeforeEach
    void init() {
        PLAYER.setBalance(0.0);
    }

    @Test
    void no_player_found_should_return_null() {

        when(playerRepository.findById(anyInt())).thenReturn(Optional.empty());

        var player2 = playerKafkaService.paymentTransaction(TRANSACTION_DTO);
        var player3 = playerKafkaService.bettingMoneyTransfer(BETTING_TRANSFER_DTO);

        Assertions.assertNull(player2);
        Assertions.assertNull(player3);
    }

    @Test
    void should_update_player_balance_with_bet_win_amount() {

        when(playerRepository.findById(BETTING_TRANSFER_DTO.getPlayerId())).thenReturn(Optional.of(PLAYER));

        playerKafkaService.bettingMoneyTransfer(BETTING_TRANSFER_DTO);

        verify(playerRepository, times(1)).findById(BETTING_TRANSFER_DTO.getPlayerId());
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(BETTING_TRANSFER_DTO.getWinAmount(), playerArgumentCaptor.getValue().getBalance());
    }

    @Test
    void should_update_player_balance_with_deposit_amount() {

        when(playerRepository.findById(TRANSACTION_DTO.getTransactionId())).thenReturn(Optional.of(PLAYER));

        playerKafkaService.paymentTransaction(TRANSACTION_DTO);

        verify(playerRepository, times(1)).findById(TRANSACTION_DTO.getTransactionId());
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(TRANSACTION_DTO.getTransactionAmount(), playerArgumentCaptor.getValue().getBalance());
    }
}
