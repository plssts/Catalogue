/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankingService {
    @Autowired
    private RevolutAccountService revService;

    @Autowired
    private DeutscheAccountService dbService;

    public AccountService retrieveAccountService(Bank bankingService){
        switch (bankingService){
            case REVOLUT:
                return revService;

            case DEUTSCHE:
                return dbService;
        }
        return null; // Should never happen since GetMapping prevents this
    }
}
