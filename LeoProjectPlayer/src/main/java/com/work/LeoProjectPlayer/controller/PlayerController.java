package com.work.LeoProjectPlayer.controller;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.PlayerLombok;
import com.work.LeoProjectPlayer.service.PlayerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/all")
    public Collection<PlayerLombok> getAll() {
        return playerService.getAll();
    }

    @GetMapping("/get_player/{id}")
    public PlayerLombok getPlayer(@PathVariable Integer id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/register")
    public PlayerLombok register(@RequestBody PlayerDTO playerDTO) {
        return playerService.register(playerDTO);
    }

    @DeleteMapping("/delete")
    public void deletePlayer(@RequestBody PlayerDTO playerDTO) {
        playerService.deletePlayer(playerDTO);
    }

    @PutMapping("/edit_player")
    public PlayerLombok editPlayer(@RequestBody PlayerDTO playerDTO) {
        return playerService.editPlayer(playerDTO);
    }
}
