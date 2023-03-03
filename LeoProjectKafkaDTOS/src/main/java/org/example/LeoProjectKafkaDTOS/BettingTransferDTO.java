package org.example.LeoProjectKafkaDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BettingTransferDTO {

    private Integer playerId;

    private Double betAmount;

    private Double winAmount;

    public BettingTransferDTO(int playerId, Double betAmount) {
        this.playerId = playerId;
        this.betAmount = betAmount;
    }
}
