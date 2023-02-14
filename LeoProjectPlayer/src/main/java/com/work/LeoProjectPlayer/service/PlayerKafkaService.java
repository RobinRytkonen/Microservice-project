package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LeoProjectKafkaDTOS.MoneyTransferDTO;
import org.springframework.stereotype.Service;

@Service
public class PlayerKafkaService {

    private static final Logger log = LogManager.getLogger(PlayerKafkaService.class);

    private final PlayerRepository playerRepository;

    public PlayerKafkaService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public MoneyTransferDTO gamingMoneyTransfer(MoneyTransferDTO moneyTransferDTO) {
        if (playerRepository.findById(moneyTransferDTO.getPlayerId()).isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        Player player = playerRepository.findById(moneyTransferDTO.getPlayerId()).get();
        player.setGamingbalance(moneyTransferDTO.getGamingbalance() + player.getGamingbalance());
        playerRepository.save(player);
        return moneyTransferDTO;
    }
}
