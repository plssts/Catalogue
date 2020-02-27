package com.j2020.repository;

import com.j2020.model.BatchOfPayments;
import com.j2020.model.GeneralAccount;
import com.j2020.model.TransactionStatusCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentBatchRepository extends JpaRepository<BatchOfPayments, Long> {
}
