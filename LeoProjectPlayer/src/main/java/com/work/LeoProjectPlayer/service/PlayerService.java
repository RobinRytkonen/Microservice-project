package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

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
            log.info("No registered player with that name and email!");
            return null;
        }
        return playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).get();
    }

    public Player register(PlayerDTO playerDTO) {
        if (playerRepository.findByEmail(playerDTO.getEmail()).isPresent()) {
            log.info("A player already registered with that email!");
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
        log.info(playerDTO);
        return newPlayer;
    }

    public Player deletePlayer(PlayerDTO playerDTO) {
        if (playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).isEmpty()) {
            log.info("No registered player with that name and email!");
            return null;
        }
        Player player = playerRepository.findByNameAndEmail(playerDTO.getName(), playerDTO.getEmail()).get();
        playerRepository.delete(player);
        log.info("Sucessfully deleted: " + player);
        return player;
    }

    public Player editPlayer(PlayerDTO playerDTO) {
        Optional<Player> existing = playerRepository.findById(playerDTO.getPlayerId());
        if (existing.isEmpty()) {
            log.info("No registered player with that name and email!");
            return null;
        }
        Player updatedPlayer = existing.get();
        BeanUtils.copyProperties(playerDTO, updatedPlayer);
        log.info("Successfully updated " + updatedPlayer);
        return playerRepository.save(updatedPlayer);
    }
}
