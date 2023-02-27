package com.work.LeoProjectPlayer.controller;

import com.work.LeoProjectPlayer.service.PlayerKafkaService;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.work.LeoProjectPlayer.util.Constants.TRANSACTION_TOPIC;
import static com.work.LeoProjectPlayer.util.Constants.BETTING_TRANSFER_TOPIC;
import static com.work.LeoProjectPlayer.util.Constants.MICROSERVICE_PROJECT_GROUP_ID;

@Component
public class PlayerKafkaController {

    private final PlayerKafkaService playerKafkaService;

    public PlayerKafkaController(PlayerKafkaService playerKafkaService) {
        this.playerKafkaService = playerKafkaService;
    }

    @KafkaListener(topics = BETTING_TRANSFER_TOPIC, groupId = MICROSERVICE_PROJECT_GROUP_ID)
    public BettingTransferDTO bettingMoneyTransfer(BettingTransferDTO bettingTransferDTO) {
        return playerKafkaService.bettingMoneyTransfer(bettingTransferDTO);
    }

    @KafkaListener(topics = TRANSACTION_TOPIC, groupId = MICROSERVICE_PROJECT_GROUP_ID)
    public TransactionDTO paymentTransaction(TransactionDTO transactionDTO) {
        return playerKafkaService.paymentTransaction(transactionDTO);
    }
}
