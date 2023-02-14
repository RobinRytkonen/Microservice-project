package com.work.LeoProjectGaming.service;

import org.example.LeoProjectKafkaDTOS.MoneyTransferDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.work.LeoProjectGaming.util.Constants.GAMING_MONEY_TRANSFER_TOPIC;

@Service
public class GamingKafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public GamingKafkaService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public MoneyTransferDTO gamingWinAndLose(MoneyTransferDTO moneyTransferDTO) {
        kafkaTemplate.send(GAMING_MONEY_TRANSFER_TOPIC, String.valueOf(moneyTransferDTO.getPlayerId()), moneyTransferDTO);
        return moneyTransferDTO;
    }
}
