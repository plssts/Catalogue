/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.service.BatchRetrievalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BatchController {
    @Value("${indexPage.readmeLink}")
    private String readmeLink;

    private BatchRetrievalService batchService;

    public BatchController(BatchRetrievalService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ok("Refer to { " + readmeLink + " } for instructions");
    }

    @GetMapping("/statuses/{batchId}")
    public ResponseEntity<Map<String, Object>> getBatchInfo(@PathVariable String batchId) {
        return batchService.getBatchData(Long.valueOf(batchId));
    }
}
