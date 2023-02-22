package org.example.LeoProjectKafkaDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BettingTransferDTO {

    private int playerId;

    private double betAmount;

    private double winAmount;

    public BettingTransferDTO(int playerId, double betAmount) {
        this.playerId = playerId;
        this.betAmount = betAmount;
    }
}
