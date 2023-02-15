package com.work.LeoProjectGaming.service;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.repository.BettingHistoryRepository;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;

import static com.work.LeoProjectGaming.util.Constants.BETTING_TRANSFER_TOPIC;

@Service
public class GamingService {

    public static final Random winOrLose = new Random();

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BettingHistoryRepository bettingHistoryRepository;

    public GamingService(KafkaTemplate<String, Object> kafkaTemplate, BettingHistoryRepository bettingHistoryRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.bettingHistoryRepository = bettingHistoryRepository;
    }

    public BettingTransferDTO bettingWinOrLose(BettingTransferDTO bettingTransferDTO) {
        Bet bet = new Bet(bettingTransferDTO.getPlayerId(), bettingTransferDTO.getBetAmount(), new Date());
        boolean roll = winOrLose.nextBoolean();
        if (roll) {
            bet.setBetWinAmount(bettingTransferDTO.getBetAmount() * 2);
            bettingTransferDTO.setBetAmount(bettingTransferDTO.getBetAmount() * 2);
        }
        if (!roll) {
            bet.setBetLoseAmount(-bettingTransferDTO.getBetAmount());
            bettingTransferDTO.setBetAmount(-bettingTransferDTO.getBetAmount());
        }
        bettingHistoryRepository.save(bet);
        kafkaTemplate.send(BETTING_TRANSFER_TOPIC, String.valueOf(bettingTransferDTO.getPlayerId()), bettingTransferDTO);
        return bettingTransferDTO;
    }
}
