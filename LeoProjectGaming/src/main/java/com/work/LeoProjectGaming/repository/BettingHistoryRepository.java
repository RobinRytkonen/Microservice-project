package com.work.LeoProjectGaming.repository;

import com.work.LeoProjectGaming.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BettingHistoryRepository extends JpaRepository<Bet, Integer> {
}
