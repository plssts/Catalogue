/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.GeneralAccount;
import com.j2020.service.PersistenceManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final PersistenceManagerService persistence;

    public AccountController(PersistenceManagerService persistence) {
        this.persistence = persistence;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<GeneralAccount>>> readAccounts() {
        Map<String, List<GeneralAccount>> outcome = persistence.returnAccounts();

        return ok(outcome);
    }
}
