/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.*;
import com.j2020.service.PersistenceManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final PersistenceManagerService persistence;

    public TransactionController(PersistenceManagerService persistence) {
        this.persistence = persistence;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<GeneralTransaction>>> readTransactions() {
        Map<String, List<GeneralTransaction>> outcome = persistence.returnTransactions();

        return ok(outcome);
    }

    @PostMapping
    public ResponseEntity<Map<String, List<PaymentResponse>>> createPayments(@RequestBody Map<String, List<GeneralPayment>> params) {
        logger.info("Creating payments for {}", params.keySet());
        Map<String, List<PaymentResponse>> outcome = persistence.processAndUpdateTransactions(params);

        return ok(outcome);
    }
}
