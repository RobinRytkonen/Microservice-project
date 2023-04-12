package com.work.LeoProjectPlayer.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column

@Entity
data class PlayerKotlin(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    var playerId: Int? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "phone_number")
    var phoneNumber: Int? = null,

    @Column(name = "location")
    var location: String? = null,

    @Column(name = "country")
    var country: String? = null,

    @Column(name = "balance")
    var balance: Double? = null,

)