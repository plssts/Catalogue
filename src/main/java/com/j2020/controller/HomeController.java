/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class HomeController {
    @Value("${indexPage.readmeLink}")
    private String readmeLink;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ok("Refer to { " + readmeLink + " } for instructions");
    }
}
