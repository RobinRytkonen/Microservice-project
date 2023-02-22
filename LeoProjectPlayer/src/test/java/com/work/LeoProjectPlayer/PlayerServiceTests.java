package com.work.LeoProjectPlayer;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import com.work.LeoProjectPlayer.service.PlayerService;
import java.util.List;
import java.util.Optional;
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
class PlayerServiceTests {

    Player player1 = new Player("Robin", "Rytkönen", "Bobzoor@gmail.com", 988765, "Vetlanda", "Sweden", 0);
    Player player2 = new Player("Robin1", "Rytkönen1", "Bobzoor@gmail.com1", 988765, "Vetlanda", "Sweden", 0);
    Player player3 = new Player("Robin2", "Rytkönen2", "Bobzoor@gmail.com2", 988765, "Vetlanda", "Sweden", 0);
    PlayerDTO dto = new PlayerDTO("Robin", "Rytkönen", "Bobzoor@gmail.com", 988765, "Vetlanda", "Sweden", 0);
    PlayerDTO dto2 = new PlayerDTO("Robin4", "Rytkönen4", "Bobzoor@gmail.com4", 9887654, "Vetlanda4", "Sweden4", 0);
    PlayerDTO registerDTO = new PlayerDTO("Robin5", "Rytkönen4", "Bobzoor@gmail.com5", 9887654, "Vetlanda4", "Sweden4", 0);

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    @Captor
    ArgumentCaptor<Player> playerArgumentCaptor;

    @Test
    void should_return_list_of_players() {

        when(playerRepository.findAll()).thenReturn(List.of(player1, player2, player3));

        var players = playerService.getAll();

        Assertions.assertEquals(3, players.size());
        Assertions.assertTrue(players.containsAll(List.of(player1, player2, player3)));
    }

    @Test
    void should_return_player() {

        when(playerRepository.findByNameAndEmail("Robin", "Bobzoor@gmail.com")).thenReturn(Optional.of(player1));

        playerService.getPlayer(dto);

        Assertions.assertEquals(player1.getName(), dto.getName());
        Assertions.assertEquals(player1.getEmail(), dto.getEmail());
        verify(playerRepository, times(1)).findByNameAndEmail("Robin", "Bobzoor@gmail.com");

    }

    @Test
    void should_not_return_player() {

        when(playerRepository.findByNameAndEmail(anyString(), anyString())).thenReturn(Optional.empty());

        var player = playerService.getPlayer(dto);

        Assertions.assertNull(player);
        verify(playerRepository, times(1)).findByNameAndEmail(anyString(), anyString());
    }

    @Test
    void should_save_player() {

        when(playerRepository.findByNameAndEmail(dto.getName(), dto.getEmail())).thenReturn(Optional.empty());

        playerService.register(dto);

        verify(playerRepository, times(1)).findByNameAndEmail("Robin", "Bobzoor@gmail.com");
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals("Robin", playerArgumentCaptor.getValue().getName());
        Assertions.assertEquals("Bobzoor@gmail.com", playerArgumentCaptor.getValue().getEmail());
    }

    @Test
    void should_not_save_player() {

        when(playerRepository.findByNameAndEmail(dto.getName(), dto.getEmail())).thenReturn(Optional.of(player1));

        playerService.register(dto);

        verify(playerRepository, times(1)).findByNameAndEmail(dto.getName(), dto.getEmail());
        verify(playerRepository, times(0)).save(any());
    }

    @Test
    void should_delete_player() {

        when(playerRepository.findByNameAndEmail("Robin", "Bobzoor@gmail.com")).thenReturn(Optional.of(player1));

        playerService.deletePlayer(dto);

        verify(playerRepository, times(1)).delete(player1);
        verify(playerRepository, times(1)).findByNameAndEmail("Robin", "Bobzoor@gmail.com");
    }

    @Test
    void should_update_player() {

        when(playerRepository.findById(dto2.getPlayerId())).thenReturn(Optional.of(player1));

        playerService.editPlayer(dto2);

        verify(playerRepository,times(1)).findById(dto2.getPlayerId());
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals("Robin4", playerArgumentCaptor.getValue().getName());
        Assertions.assertEquals("Rytkönen4", playerArgumentCaptor.getValue().getLastName());
        Assertions.assertEquals("Bobzoor@gmail.com4", playerArgumentCaptor.getValue().getEmail());
        Assertions.assertEquals(9887654, playerArgumentCaptor.getValue().getPhoneNumber());
        Assertions.assertEquals("Vetlanda4", playerArgumentCaptor.getValue().getLocation());
        Assertions.assertEquals("Sweden4", playerArgumentCaptor.getValue().getCountry());


    }
}
