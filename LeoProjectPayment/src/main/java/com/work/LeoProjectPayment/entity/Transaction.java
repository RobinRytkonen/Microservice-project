package com.work.LeoProjectPayment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Integer id;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "type")
    private String type;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "date")
    private Date date;

    public Transaction(int transactionId, String type, Double transactionAmount, Date date) {
        this.transactionId = transactionId;
        this.type = type;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }
}
