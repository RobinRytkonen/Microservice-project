package com.work.LeoProjectPlayer.dtos;

import lombok.Data;

@Data
public class PlayerDTO {

    private int playerId;

    private String name;

    private String lastName;

    private String email;

    private int phoneNumber;

    private String location;

    private String country;

    private double balance;

    public PlayerDTO(String name, String lastName, String email, int phoneNumber, String location, String country, double balance) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.country = country;
        this.balance = balance;
    }
}
