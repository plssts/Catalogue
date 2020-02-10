/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.service.BankingServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.ResponseEntity.ok;

// FIXME || return response entities though, not strings - get rid of strings almost entirely;
// FIXME pakeisti linked value map i normalu hash map

@RestController
public class AccountController {
    private BankingServiceFactory bankingService;

    public AccountController(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping({"/{bank}/accounts/{id}", "/{bank}/accounts"})
    public ResponseEntity<List<? extends Account>> readRevoAccount(@PathVariable String bank, @PathVariable(required = false) String id){
        List<? extends Account> accounts = null;
        if (!StringUtils.isAllBlank(id)){
            System.out.println("Account: " + id);
            Optional<String> str = Optional.of(id);
            accounts = bankingService.retrieveAccountService(Bank.valueOf(StringUtils.upperCase(bank))).retrieveAccountData(str);
        } else {
            System.out.println("Account was not provided");
            Optional<String> str = Optional.ofNullable(null);
            accounts = bankingService.retrieveAccountService(Bank.valueOf(StringUtils.upperCase(bank))).retrieveAccountData(str);
        }

        return ok(accounts);
    }
}
