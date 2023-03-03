package com.work.LeoProjectPlayer.repository;

import com.work.LeoProjectPlayer.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<Player> findByNameAndEmail(String name, String email);

    Optional<Player> findByEmail(String email);

}
