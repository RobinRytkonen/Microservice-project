package com.work.LeoProjectGaming.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Collection;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Playerid")
    private int playerId;

    @Column(name = "Betamount")
    private double betAmount;

    @Column(name = "Betwinamount")
    private double betWinAmount;

    @Column(name = "Betloseamount")
    private double betLoseAmount;

    @Column(name = "Date")
    private Date date;

    public Bet(int playerId, double betAmount, Date date) {
        this.playerId = playerId;
        this.betAmount = betAmount;
        this.date = date;
    }
}
