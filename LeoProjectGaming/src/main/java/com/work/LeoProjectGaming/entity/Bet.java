package com.work.LeoProjectGaming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "bet_amount")
    private Double betAmount;

    @Column(name = "win_amount")
    private Double winAmount;

    @Column(name = "date")
    private Date date;

    public Bet(int playerId, Double betAmount, Date date) {
        this.playerId = playerId;
        this.betAmount = betAmount;
        this.date = date;
    }
}
