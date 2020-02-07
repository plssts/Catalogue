/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.Bank;
import org.springframework.stereotype.Service;

@Service
public class BankingServiceFactory {
    private RevolutAccountService revService;
    private DeutscheAccountService dbService;

    public BankingServiceFactory(RevolutAccountService revService, DeutscheAccountService dbService) {
        this.revService = revService;
        this.dbService = dbService;
    }

    public AccountService retrieveAccountService(Bank bankingService){
        switch (bankingService){
            case REVOLUT:
                return revService;

            case DEUTSCHE:
                return dbService;
        }
        throw new RuntimeException("Should never happen since GetMapping prevents this");
    }
}
