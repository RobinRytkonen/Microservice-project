package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.entity.PlayerLombok;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerKafkaService {

    private static final Logger log = LogManager.getLogger(PlayerKafkaService.class);

    private final PlayerRepository playerRepository;

    public PlayerKafkaService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public BettingTransferDTO bettingMoneyTransfer(BettingTransferDTO bettingTransferDTO) {
        Optional<PlayerLombok> optionalPlayer = playerRepository.findById(bettingTransferDTO.getPlayerId());
        if (optionalPlayer.isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        PlayerLombok player = optionalPlayer.get();
        player.setBalance(bettingTransferDTO.getWinAmount() + player.getBalance());
        playerRepository.save(player);
        return bettingTransferDTO;
    }

    public TransactionDTO paymentTransaction(TransactionDTO transactionDTO) {
        Optional<PlayerLombok> optionalPlayer = playerRepository.findById(transactionDTO.getTransactionId());
        if (optionalPlayer.isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        PlayerLombok player = optionalPlayer.get();
        player.setBalance(transactionDTO.getTransactionAmount() + player.getBalance());
        playerRepository.save(player);
        return transactionDTO;
    }
}
