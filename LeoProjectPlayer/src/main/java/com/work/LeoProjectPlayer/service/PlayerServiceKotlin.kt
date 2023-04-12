package com.work.LeoProjectPlayer.service

import com.work.LeoProjectPlayer.dtos.PlayerDTO
import com.work.LeoProjectPlayer.entity.PlayerLombok
import com.work.LeoProjectPlayer.repository.PlayerRepository
import com.work.LeoProjectPlayer.util.Constants.NO_PLAYER_PRESENT
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class PlayerServiceKotlin(private val playerRepository: PlayerRepository) // primär konstruktor

{

    fun getAll(): Collection<PlayerLombok> {
        return playerRepository.findAll()
    }

    fun getPlayer(id: Int): PlayerLombok? { // kollar om det är null!
        val player = playerRepository.findById(id)
        if (player.isEmpty) {
            log.info(NO_PLAYER_PRESENT)
            return null
        }
        return player.get() // inga avslutnings taggar
    }

    fun register(playerDTO: PlayerDTO): PlayerLombok? {
        val player = playerRepository.findByNameAndEmail(playerDTO.name, playerDTO.email)
        if (player.isEmpty) {
            val newPlayer =
                PlayerLombok( // Behöver inte new! när man skapar ett nytt objekt
                    playerDTO.name, // Inga get i koden
                    playerDTO.lastName,
                    playerDTO.email,
                    playerDTO.phoneNumber,
                    playerDTO.location,
                    playerDTO.country,
                    playerDTO.balance
                )
            playerRepository.save(newPlayer)
            log.info(newPlayer)
            return newPlayer
        }
        log.info("Player with that name or email already registered!")
        return null
    }

    fun deletePlayer(playerDTO: PlayerDTO) {
        val player = playerRepository.findByNameAndEmail(playerDTO.name, playerDTO.email)
        if (player.isEmpty) {
            log.info(NO_PLAYER_PRESENT)
        }
        playerRepository.delete(player.get())
        log.info(player)
    }

    fun editPlayer(playerDTO: PlayerDTO): PlayerLombok? {
        val player = playerRepository.findById(playerDTO.playerId)
        if (player.isEmpty) {
            log.info("No registered player with that id!")
            return null
        }
        //val updatedPlayer: Player = player.get() // Typad
        val updatedPlayer = player.get()
        updatedPlayer.name = playerDTO.name
        updatedPlayer.lastName = playerDTO.lastName
        updatedPlayer.email = playerDTO.email
        updatedPlayer.phoneNumber = playerDTO.phoneNumber
        updatedPlayer.location = playerDTO.location
        updatedPlayer.country = playerDTO.country
        log.info(updatedPlayer)
        return playerRepository.save(updatedPlayer)
    }

    companion object { // kolla upp companion object vs static java
        private val log: Logger = LogManager.getLogger(PlayerServiceKotlin)
    }
}