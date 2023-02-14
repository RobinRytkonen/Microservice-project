package com.work.LeoProjectPayment.repository;

import com.work.LeoProjectPayment.entity.PaymentAccount;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentAccount, Integer> {
}
