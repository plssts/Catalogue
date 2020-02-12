/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.Bank;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.springframework.stereotype.Service;

@Service
public class BankingServiceFactory {
    private RevolutAccountService revAccountService;
    private DeutscheAccountService dbAccountService;
    private RevolutTransactionService revTransactionService;
    private DeutscheTransactionService dbTransactionService;

    public BankingServiceFactory(RevolutAccountService revAccountService, DeutscheAccountService dbAccountService,
                                 RevolutTransactionService revTransactionService, DeutscheTransactionService dbTransactionService) {
        this.revAccountService = revAccountService;
        this.dbAccountService = dbAccountService;
        this.revTransactionService = revTransactionService;
        this.dbTransactionService = dbTransactionService;
    }

    public AccountService retrieveAccountService(Bank bankingService) {
        switch (bankingService) {
            case REVOLUT:
                return revAccountService;

            case DEUTSCHE:
                return dbAccountService;
        }
        throw new RuntimeException("Should never happen since GetMapping prevents this");
    }

    public TransactionService retrieveTransactionService(Bank bankingService) {
        switch (bankingService) {
            case REVOLUT:
                return revTransactionService;

            case DEUTSCHE:
                return dbTransactionService;
        }
        throw new RuntimeException("Should never happen since GetMapping prevents this");
    }
}
