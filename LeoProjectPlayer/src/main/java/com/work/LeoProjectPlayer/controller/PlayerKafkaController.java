package com.work.LeoProjectPlayer.controller;

import com.work.LeoProjectPlayer.service.PlayerKafkaService;
import org.example.LeoProjectKafkaDTOS.MoneyTransferDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import static com.work.LeoProjectPlayer.util.Constants.GAMING_MONEY_TRANSFER_GROUP_ID;
import static com.work.LeoProjectPlayer.util.Constants.GAMING_MONEY_TRANSFER_TOPIC;

@Component
@RestController
public class PlayerKafkaController {

    private final PlayerKafkaService playerKafkaService;

    public PlayerKafkaController(PlayerKafkaService playerKafkaService) {
        this.playerKafkaService = playerKafkaService;
    }

    @KafkaListener(topics = GAMING_MONEY_TRANSFER_TOPIC, groupId = GAMING_MONEY_TRANSFER_GROUP_ID)
    public MoneyTransferDTO gamingMoneyTransfer(MoneyTransferDTO moneyTransferDTO) {
        return playerKafkaService.gamingMoneyTransfer(moneyTransferDTO);
    }

}
