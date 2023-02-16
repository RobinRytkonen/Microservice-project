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

    private final Random win = new Random();
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BettingHistoryRepository bettingHistoryRepository;

    public GamingService(KafkaTemplate<String, Object> kafkaTemplate, BettingHistoryRepository bettingHistoryRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.bettingHistoryRepository = bettingHistoryRepository;
    }

    public Bet bet(BettingTransferDTO dto) {
        Bet bet = new Bet(dto.getPlayerId(), dto.getBetAmount(), new Date());
        if (win.nextBoolean()) {
            bet.setWinAmount(dto.getBetAmount() * win.nextInt(100));
            dto.setWinAmount(bet.getWinAmount() - dto.getBetAmount());
        } else {
            bet.setWinAmount(0);
            dto.setWinAmount(0 - dto.getBetAmount());
        }
        bettingHistoryRepository.save(bet);
        kafkaTemplate.send(BETTING_TRANSFER_TOPIC, String.valueOf(dto.getPlayerId()), dto);
        return bet;
    }
}
