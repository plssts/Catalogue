/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.TokenFetchException;
import com.j2020.service.BankingServiceFactory;
import org.apache.commons.lang3.StringUtils;
//import com.sun.tools.javac.util.StringUtils;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

// FIXME || return response entities though, not strings - get rid of strings almost entirely;
// FIXME || change GetMapping to introduce Account model (both for Revolut and Deutsche) to
// FIXME || have a useful factory - for example GET /{bank}/{id} - and then make objects according
// FIXME || to {bank} from your factory;
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

        System.out.println("Returning");
        return ok(accounts);
    }

    /*@GetMapping(value="/revolut/{accountId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> readSpecificRevoAccount(@PathVariable(required = false) String accountId){
        try {
            accountId.isEmpty();// FIXME ...
            StringUtils.hasText(accountId); // FIXME this is better - not null, length >0 and not whitespace

            Account account = bankingService.retrieveAccountService(Bank.REVOLUT).retrieveSpecificAccount(accountId);
            return ok(account);
        } catch (TokenFetchException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Padavei bloga para");
        }
    }

    @GetMapping(value="/deutsche", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> readDeutAccount(){
        try {
            return ok(bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData());
        } catch (TokenFetchException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @GetMapping(value="/deutsche/{iban}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> readSpecificDeutAccount(@PathVariable String iban){
        try {
            return ok(bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveSpecificAccount(iban));
        } catch (TokenFetchException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }*/
}
