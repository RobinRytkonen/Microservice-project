package com.work.LeoProjectPlayer.controller;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.Player;
import com.work.LeoProjectPlayer.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/all")
    public Collection<Player> getAll() {
        return playerService.getAll();
    }

    @GetMapping("/get_player")
    public Player getPlayer(@RequestBody PlayerDTO playerDTO) {
        return playerService.getPlayer(playerDTO);
    }

    @PostMapping("/register")
    public Player register(@RequestBody PlayerDTO playerDTO) {
        return playerService.register(playerDTO);
    }

    @DeleteMapping("/delete")
    public Player deletePlayer(@RequestBody PlayerDTO playerDTO) {
        return playerService.deletePlayer(playerDTO);
    }

    @PutMapping("/edit_player")
    public Player editPlayer(@RequestBody PlayerDTO playerDTO) {
        return playerService.editPlayer(playerDTO);
    }
}
