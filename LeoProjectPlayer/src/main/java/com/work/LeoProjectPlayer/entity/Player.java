package com.work.LeoProjectPlayer.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int playerId;

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

    public Player(String name, String lastName, String email, int phoneNumber, String location, String country, double balance) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.country = country;
        this.balance = balance;
    }
}
