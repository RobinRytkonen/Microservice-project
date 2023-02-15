package com.work.LeoProjectGaming.controller;

import com.work.LeoProjectGaming.service.GamingKafkaService;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamingKafkaController {

    private final GamingKafkaService gamingKafkaService;

    public GamingKafkaController(GamingKafkaService gamingKafkaService) {
        this.gamingKafkaService = gamingKafkaService;
    }

    @PostMapping("/bet")
    public BettingTransferDTO bettingWinOrLose(@RequestBody BettingTransferDTO bettingTransferDTO) {
        return gamingKafkaService.bettingWinOrLose(bettingTransferDTO);
    }
}
