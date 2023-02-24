package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Collection;

import static com.work.LeoProjectPlayer.util.Constants.NO_PLAYER_PRESENT;

@Service
public class PlayerService {

    private static final Logger log = LogManager.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Collection<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player getPlayer(int id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            log.info(NO_PLAYER_PRESENT);
            return null;
        }
        return player.get();
    }

    public Player register(PlayerDTO playerDTO) {
        Optional<Player> player = playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail());
        if (player.isEmpty()) {
            Player newPlayer = new Player(
                    playerDTO.getName(),
                    playerDTO.getLastName(),
                    playerDTO.getEmail(),
                    playerDTO.getPhoneNumber(),
                    playerDTO.getLocation(),
                    playerDTO.getCountry(),
                    playerDTO.getBalance());
            playerRepository.save(newPlayer);
            log.info(newPlayer);
            return newPlayer;
        }
        log.info("Player with that name or email already registered!");
        return null;
    }

    public void deletePlayer(PlayerDTO playerDTO) {
        Optional<Player> player = playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail());
        if (player.isEmpty()) {
            log.info(NO_PLAYER_PRESENT);
        }
        playerRepository.delete(player.get());
        log.info(player);
    }

    public Player editPlayer(PlayerDTO playerDTO) {
        Optional<Player> player = playerRepository.findById(playerDTO.getPlayerId());
        if (player.isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        Player updatedPlayer = player.get();
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
