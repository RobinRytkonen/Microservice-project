package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.PlayerLombok;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static com.work.LeoProjectPlayer.util.Constants.NO_PLAYER_PRESENT;

@Service
public class PlayerService {

    private static final Logger log = LogManager.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Collection<PlayerLombok> getAll() {
        return playerRepository.findAll();
    }

    public PlayerLombok getPlayer(int id) {
        Optional<PlayerLombok> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            log.info(NO_PLAYER_PRESENT);
            return null;
        }
        return player.get();
    }

    public PlayerLombok register(PlayerDTO playerDTO) {
        Optional<PlayerLombok> player = playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail());
        if (player.isEmpty()) {
            PlayerLombok newPlayer = new PlayerLombok(
                    playerDTO.getName(),
                    playerDTO.getLastName(),
                    playerDTO.getEmail(),
                    playerDTO.getPhoneNumber(),
                    playerDTO.getLocation(),
                    playerDTO.getCountry(),
                    playerDTO.getBalance()
            );
            playerRepository.save(newPlayer);
            log.info(newPlayer);
            return newPlayer;
        }
        log.info("Player with that name or email already registered!");
        return null;
    }

    public void deletePlayer(PlayerDTO playerDTO) {
        Optional<PlayerLombok> player = playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail());
        if (player.isEmpty()) {
            log.info(NO_PLAYER_PRESENT);
        }
        playerRepository.delete(player.get());
        log.info(player);
    }

    public PlayerLombok editPlayer(PlayerDTO playerDTO) {
        Optional<PlayerLombok> player = playerRepository.findById(playerDTO.getPlayerId());
        if (player.isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        PlayerLombok updatedPlayer = player.get();
        updatedPlayer.setName(playerDTO.getName());
        updatedPlayer.setLastName(playerDTO.getLastName());
        updatedPlayer.setEmail(playerDTO.getEmail());
        updatedPlayer.setPhoneNumber(playerDTO.getPhoneNumber());
        updatedPlayer.setLocation(playerDTO.getLocation());
        updatedPlayer.setCountry(playerDTO.getCountry());
        log.info(updatedPlayer);
        return playerRepository.save(updatedPlayer);
    }
}
