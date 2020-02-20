/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.service.PersistenceManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final PersistenceManagerService persistence;

    public AccountController(PersistenceManagerService persistence) {
        this.persistence = persistence;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Account>>> readAccounts() {
        Map<String, List<Account>> outcome = persistence.returnAccounts();

        return ok(outcome);
    }
}
