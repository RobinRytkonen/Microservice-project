package com.work.LeoProjectGaming.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    private int id;

    @Column(name = "player_id")
    private int playerId;

    @Column(name = "bet_amount")
    private double betAmount;

    @Column(name = "win_amount")
    private double winAmount;

    @Column(name = "date")
    private Date date;

    public Bet(int playerId, double betAmount, Date date) {
        this.playerId = playerId;
        this.betAmount = betAmount;
        this.date = date;
    }
}
