package com.work.LeoProjectPlayer;

import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import com.work.LeoProjectPlayer.service.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.work.LeoProjectPlayer.TestConstants.LIST_OF_PLAYERS;
import static com.work.LeoProjectPlayer.TestConstants.PLAYER;
import static com.work.LeoProjectPlayer.TestConstants.PLAYER_DTO;
import static com.work.LeoProjectPlayer.TestConstants.PLAYER_EDIT_DTO;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    @Captor
    ArgumentCaptor<Player> playerArgumentCaptor;

    @Test
    void should_return_list_of_players() {

        when(playerRepository.findAll()).thenReturn(LIST_OF_PLAYERS);

        var players = playerService.getAll();

        Assertions.assertEquals(LIST_OF_PLAYERS.size(), players.size());
        Assertions.assertTrue(players.containsAll(LIST_OF_PLAYERS));
    }

    @Test
    void should_return_player() {

        when(playerRepository.findById(PLAYER_DTO.getPlayerId())).thenReturn(Optional.of(PLAYER));

        playerService.getPlayer(PLAYER_DTO.getPlayerId());

        Assertions.assertEquals(PLAYER.getName(), PLAYER_DTO.getName());
        Assertions.assertEquals(PLAYER.getEmail(), PLAYER_DTO.getEmail());
        verify(playerRepository, times(1)).findById(PLAYER_DTO.getPlayerId());

    }

    @Test
    void should_not_return_player() {

        when(playerRepository.findById(anyInt())).thenReturn(Optional.empty());

        var player = playerService.getPlayer(PLAYER_DTO.getPlayerId());

        Assertions.assertNull(player);
        verify(playerRepository, times(1)).findById(anyInt());
    }

    @Test
    void should_save_player() {

        when(playerRepository.findByNameAndEmail(PLAYER_DTO.getName(), PLAYER_DTO.getEmail())).thenReturn(Optional.empty());

        playerService.register(PLAYER_DTO);

        verify(playerRepository, times(1)).findByNameAndEmail(PLAYER.getName(), PLAYER.getEmail());
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(PLAYER.getName(), playerArgumentCaptor.getValue().getName());
        Assertions.assertEquals(PLAYER.getEmail(), playerArgumentCaptor.getValue().getEmail());
    }

    @Test
    void should_not_save_player() {

        when(playerRepository.findByNameAndEmail(PLAYER_DTO.getName(), PLAYER_DTO.getEmail())).thenReturn(Optional.of(PLAYER));

        playerService.register(PLAYER_DTO);

        verify(playerRepository, times(1)).findByNameAndEmail(PLAYER_DTO.getName(), PLAYER_DTO.getEmail());
        verify(playerRepository, times(0)).save(any());
    }

    @Test
    void should_delete_player() {

        when(playerRepository.findByNameAndEmail(PLAYER.getName(), PLAYER.getEmail())).thenReturn(Optional.of(PLAYER));

        playerService.deletePlayer(PLAYER_DTO);

        verify(playerRepository, times(1)).delete(PLAYER);
        verify(playerRepository, times(1)).findByNameAndEmail(PLAYER.getName(), PLAYER.getEmail());
    }

    @Test
    void should_update_player() {

        when(playerRepository.findById(PLAYER_EDIT_DTO.getPlayerId())).thenReturn(Optional.of(PLAYER));

        playerService.editPlayer(PLAYER_EDIT_DTO);

        verify(playerRepository,times(1)).findById(PLAYER_EDIT_DTO.getPlayerId());
        verify(playerRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(PLAYER_EDIT_DTO.getName(), playerArgumentCaptor.getValue().getName());
        Assertions.assertEquals(PLAYER_EDIT_DTO.getLastName(), playerArgumentCaptor.getValue().getLastName());
        Assertions.assertEquals(PLAYER_EDIT_DTO.getEmail(), playerArgumentCaptor.getValue().getEmail());
        Assertions.assertEquals(PLAYER_EDIT_DTO.getPhoneNumber(), playerArgumentCaptor.getValue().getPhoneNumber());
        Assertions.assertEquals(PLAYER_EDIT_DTO.getLocation(), playerArgumentCaptor.getValue().getLocation());
        Assertions.assertEquals(PLAYER_EDIT_DTO.getCountry(), playerArgumentCaptor.getValue().getCountry());
    }
}
