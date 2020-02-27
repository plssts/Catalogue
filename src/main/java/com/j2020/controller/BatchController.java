/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.BatchOfPayments;
import com.j2020.repository.PaymentBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class BatchController {
    @Value("${indexPage.readmeLink}")
    private String readmeLink;

    // FIXME move these to services later
    @Autowired
    private PaymentBatchRepository batchRepository;
    // FIXME move these to services later ^^^

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ok("Refer to { " + readmeLink + " } for instructions");
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchOfPayments> getBatchInfo(@PathVariable String batchId) {
        // FIXME move logic to dedicated service
        Optional<BatchOfPayments> batch = batchRepository.findById(Long.valueOf(batchId));
        if (batch.isPresent()) {
            return ok(batch.get());
        }
        return new ResponseEntity<>(new BatchOfPayments(), HttpStatus.NOT_FOUND);
    }
}
