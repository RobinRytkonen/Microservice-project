package com.work.LeoProjectGaming.controller;

import com.work.LeoProjectGaming.entity.Bet;
import com.work.LeoProjectGaming.service.GamingService;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GamingController {

    private final GamingService gamingService;

    public GamingController(GamingService gamingService) {
        this.gamingService = gamingService;
    }

    @PostMapping("/bet")
    public Bet bet(@RequestBody BettingTransferDTO bettingTransferDTO) {
        return gamingService.bet(bettingTransferDTO);
    }
}
