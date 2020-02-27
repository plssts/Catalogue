package com.j2020.repository;

import com.j2020.model.BatchOfPayments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentBatchRepository extends JpaRepository<BatchOfPayments, Long> {
}
