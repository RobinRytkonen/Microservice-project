package com.work.LeoProjectPlayer;

import com.work.LeoProjectPlayer.dtos.PlayerDTO;
import com.work.LeoProjectPlayer.entity.Player;
import org.example.LeoProjectKafkaDTOS.BettingTransferDTO;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;

import java.util.List;

public class TestConstants {

    private TestConstants() {
    }

    public static final List<Player> LIST_OF_PLAYERS = List.of(
            new Player(1,"Robin", "Rytkonen", "Bobzoor@gmail.com", 988765, "Vetlanda", "Sweden", 0),
            new Player(2,"Robin1", "Rytkonen1", "Bobzoor@gmail.com1", 988765, "Vetlanda", "Sweden", 0),
            new Player(3,"Robin2", "Rytkonen2", "Bobzoor@gmail.com2", 988765, "Vetlanda", "Sweden", 0)
    );

    public static final Player PLAYER = new Player(1,"Robin", "Rytkonen", "Bobzoor@gmail.com", 988765, "Vetlanda", "Sweden", 0);
    public static final PlayerDTO PLAYER_CREATE_DTO = new PlayerDTO(5,"Robin5", "Rytkonen", "Bobzoor@gmail.com5", 988765, "Vetlanda", "Sweden", 0);
    public static final PlayerDTO PLAYER_DTO = new PlayerDTO(1,"Robin", "Rytkonen", "Bobzoor@gmail.com", 988765, "Vetlanda", "Sweden", 0);
    public static final PlayerDTO PLAYER_EDIT_DTO = new PlayerDTO(1,"Robin4", "Rytkonen4", "Bobzoor@gmail.com4", 9887654, "Vetlanda4", "Sweden4", 0);
    public static final BettingTransferDTO BETTING_TRANSFER_DTO = new BettingTransferDTO(1, 100, 1000);
    public static final TransactionDTO TRANSACTION_DTO = new TransactionDTO(1,"deposit", 50000);
}
