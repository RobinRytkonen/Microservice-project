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

    private double gamingbalance;
}
