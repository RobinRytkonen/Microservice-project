package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
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

    public Player getPlayer(PlayerDTO playerDTO) {
        if (playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).isEmpty()) {
            log.info(NO_PLAYER_PRESENT);
            return null;
        }
        return playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).get();
    }

    public Player register(PlayerDTO playerDTO) {
        if (playerRepository.findByEmail(playerDTO.getEmail()).isPresent()) {
            log.info(NO_PLAYER_PRESENT);
            return null;
        }
        Player newPlayer = new Player(playerDTO.getName(),
                playerDTO.getLastName(),
                playerDTO.getEmail(),
                playerDTO.getPhoneNumber(),
                playerDTO.getLocation(),
                playerDTO.getCountry(),
                playerDTO.getGamingbalance());
        playerRepository.save(newPlayer);
        log.info(newPlayer);
        return newPlayer;
    }

    public Player deletePlayer(PlayerDTO playerDTO) {
        if (playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).isEmpty()) {
            log.info(NO_PLAYER_PRESENT);
            return null;
        }
        Player player = playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).get();
        log.info(player);
        return player;
    }

    public Player editPlayer(PlayerDTO playerDTO) {
        if (playerRepository.findById(playerDTO.getPlayerId()).isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        Player updatedPlayer = playerRepository.findById(playerDTO.getPlayerId()).get();
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
