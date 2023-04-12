package com.work.LeoProjectPlayer.repository;

import com.work.LeoProjectPlayer.entity.PlayerLombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerLombok, Integer> {

    Optional<PlayerLombok> findByNameAndEmail(String name, String email);

    Optional<PlayerLombok> findByEmail(String email);

}
