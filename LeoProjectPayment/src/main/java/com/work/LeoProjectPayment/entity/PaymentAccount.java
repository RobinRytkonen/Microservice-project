package com.work.LeoProjectPayment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class PaymentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "PaymentId")
    private int paymentId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Last name")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone number")
    private int phoneNumber;

    @Column(name = "Location")
    private String location;

    @Column(name = "Country")
    private String country;

    @Column(name = "Balance")
    private double balance;

    public PaymentAccount(String name, String lastName, String email, int phoneNumber, String location, String country, double balance) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.country = country;
        this.balance = balance;
    }
}
