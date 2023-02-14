package com.work.LeoProjectGaming.controller;

import com.work.LeoProjectGaming.service.GamingKafkaService;
import org.example.LeoProjectKafkaDTOS.MoneyTransferDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamingKafkaController {

    private final GamingKafkaService gamingKafkaService;

    public GamingKafkaController(GamingKafkaService gamingKafkaService) {
        this.gamingKafkaService = gamingKafkaService;
    }

    @PostMapping("/moneyTransfer")
    public MoneyTransferDTO gamingWinAndLose(@RequestBody MoneyTransferDTO moneyTransferDTO) {
        return gamingKafkaService.gamingWinAndLose(moneyTransferDTO);
    }
}
