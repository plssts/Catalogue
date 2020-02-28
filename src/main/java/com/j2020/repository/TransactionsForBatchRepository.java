/**
 * @author Paulius Staisiunas
 */

package com.j2020.repository;

import com.j2020.model.TransactionStatusCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsForBatchRepository extends JpaRepository<TransactionStatusCheck, String> {
    List<TransactionStatusCheck> findAllByBatchId(Long batchId);
}
