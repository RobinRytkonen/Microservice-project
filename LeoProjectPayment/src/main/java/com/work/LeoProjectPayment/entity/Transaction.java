package com.work.LeoProjectPayment.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "type")
    private String type;

    @Column(name = "transaction_amount")
    private int transactionAmount;

    @Column(name = "date")
    private Date date;

    public Transaction(int transactionId, String type, int transactionAmount, Date date) {
        this.transactionId = transactionId;
        this.type = type;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }
}
