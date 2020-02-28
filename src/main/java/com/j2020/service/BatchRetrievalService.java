/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.BatchOfPayments;
import com.j2020.model.TransactionStatusCheck;
import com.j2020.repository.PaymentBatchRepository;
import com.j2020.repository.TransactionsForBatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class BatchRetrievalService {
    private PaymentBatchRepository batchRepository;
    private TransactionsForBatchRepository transactions;

    public BatchRetrievalService(PaymentBatchRepository batchRepository, TransactionsForBatchRepository transactions) {
        this.batchRepository = batchRepository;
        this.transactions = transactions;
    }

    public ResponseEntity<Map<String, Object>> getBatchData(Long batchId) {
        Map<String, Object> outcome = new LinkedHashMap<>();
        Optional<BatchOfPayments> batch = batchRepository.findById(batchId);

        if (batch.isPresent()) {
            List<TransactionStatusCheck> statuses = transactions.findAllByBatchId(batch.get().getId());
            outcome.put("Total payments", batch.get().getCountOfAllPayments());
            outcome.put("Processed payments", batch.get().getCountOfProcessedPayments());
            outcome.put("Transactions", statuses);
            return ok(outcome);
        }

        outcome.put("The bach doesn't exist.", "Verify the id is correct");
        return new ResponseEntity<>(outcome, HttpStatus.NOT_FOUND);
    }
}
