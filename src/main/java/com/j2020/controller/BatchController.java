/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.BatchOfPayments;
import com.j2020.model.TransactionStatusCheck;
import com.j2020.repository.PaymentBatchRepository;
import com.j2020.repository.TransactionsForBatchRepository;
import com.j2020.service.BatchRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BatchController {
    @Value("${indexPage.readmeLink}")
    private String readmeLink;

    // FIXME move these to services later
    @Autowired
    private PaymentBatchRepository batchRepository;
    @Autowired
    private TransactionsForBatchRepository transactions;

    private BatchRetrievalService batchService;
    // FIXME move these to services later ^^^


    public BatchController(BatchRetrievalService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ok("Refer to { " + readmeLink + " } for instructions");
    }

    @GetMapping("/statuses/{batchId}")
    public ResponseEntity<Map<String, Object>> getBatchInfo(@PathVariable String batchId) {
        // FIXME move logic to dedicated service
        /*Optional<BatchOfPayments> batch = batchRepository.findById(Long.valueOf(batchId));
        if (batch.isPresent()) {
            List<TransactionStatusCheck> statuses = transactions.findAllByBopid(batch.get().getId());
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("Total payments", batch.get().getCountOfAllPayments());
            response.put("Processed payments", batch.get().getCountOfProcessedPayments());
            response.put("Transactions", statuses);
            return ok(response);
        }
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_FOUND);*/
        return batchService.getBatchData(Long.valueOf(batchId));
    }
}
