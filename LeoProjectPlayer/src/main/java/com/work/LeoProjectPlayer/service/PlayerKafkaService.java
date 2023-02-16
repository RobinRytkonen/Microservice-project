package com.work.LeoProjectPlayer.service;

import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public class PlayerKafkaService {

    private static final Logger log = LogManager.getLogger(PlayerKafkaService.class);

    private final PlayerRepository playerRepository;

    public PlayerKafkaService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public BettingTransferDTO bettingMoneyTransfer(BettingTransferDTO bettingTransferDTO) {
        if (playerRepository.findById(bettingTransferDTO.getPlayerId()).isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        Player player = playerRepository.findById(bettingTransferDTO.getPlayerId()).get();
        player.setBalance(bettingTransferDTO.getBetAmount() + player.getBalance());
        playerRepository.save(player);
        return bettingTransferDTO;
    }

    public TransactionDTO paymentTransaction(TransactionDTO transactionDTO) {
        if (playerRepository.findById(transactionDTO.getTransactionId()).isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        Player player = playerRepository.findById(transactionDTO.getTransactionId()).get();
        player.setBalance(transactionDTO.getTransactionAmount() + player.getBalance());
        playerRepository.save(player);
        return transactionDTO;
    }
}
