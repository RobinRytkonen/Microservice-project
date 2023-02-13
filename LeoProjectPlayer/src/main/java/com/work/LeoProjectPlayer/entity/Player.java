package com.work.LeoProjectPlayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerId")
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

    @Column(name = "GamingBalance")
    private double gamingbalance;

}
