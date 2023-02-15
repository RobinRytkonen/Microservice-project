package com.work.LeoProjectPayment.repository;

import com.work.LeoProjectPayment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
