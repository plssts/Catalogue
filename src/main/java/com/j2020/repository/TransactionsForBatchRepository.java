package com.j2020.repository;

import com.j2020.model.BatchOfPayments;
import com.j2020.model.TransactionStatusCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsForBatchRepository extends JpaRepository<TransactionStatusCheck, Long> {
}
