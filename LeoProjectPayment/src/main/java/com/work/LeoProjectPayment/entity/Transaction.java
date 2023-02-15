package com.work.LeoProjectPayment.entity;

import jakarta.persistence.*;
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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Transaction_id")
    private int transactionId;

    @Column(name = "Type")
    private String type;

    @Column(name = "Transaction_amount")
    private int transactionAmount;

    @Column(name = "Date")
    private Date date;

    public Transaction(int transactionId, String type, int transactionAmount, Date date) {
        this.transactionId = transactionId;
        this.type = type;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }
}
