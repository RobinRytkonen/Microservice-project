package com.work.LeoProjectGaming.service;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.repository.BettingHistoryRepository;
import com.work.LeoProjectGaming.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.work.LeoProjectGaming.util.Constants.BETTING_TRANSFER_TOPIC;

@Service
public class GamingService {

    private static final Logger log = LogManager.getLogger(GamingService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BettingHistoryRepository bettingHistoryRepository;

    public GamingService(KafkaTemplate<String, Object> kafkaTemplate, BettingHistoryRepository bettingHistoryRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.bettingHistoryRepository = bettingHistoryRepository;
    }

    public Bet bet(BettingTransferDTO dto) {
        Bet bet = new Bet(dto.getPlayerId(), dto.getBetAmount(), new Date());
        play(dto, bet);
        bettingHistoryRepository.save(bet);
        kafkaTemplate.send(BETTING_TRANSFER_TOPIC, String.valueOf(dto.getPlayerId()), dto);
        return bet;
    }

    public void play(BettingTransferDTO dto, Bet bet) {

        if (RandomUtil.getRandomBoolean()) {
            bet.setWinAmount(dto.getBetAmount() * RandomUtil.getRandomInteger());
            dto.setWinAmount(bet.getWinAmount() - dto.getBetAmount());
        } else {
            bet.setWinAmount(0.0);
            dto.setWinAmount(0 - dto.getBetAmount());
        }
    }

    public Bet getBettingHistoryById(int id) {
        Optional<Bet> bet = bettingHistoryRepository.findById(id);
        if (bet.isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        return bet.get();
    }
}
