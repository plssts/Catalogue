/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.Bank;
import com.j2020.model.BankNotSupportedException;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BankingServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(BankingServiceFactory.class);

    private final RevolutAccountService revAccountService;
    private final DeutscheAccountService dbAccountService;
    private final RevolutTransactionService revTransactionService;
    private final DeutscheTransactionService dbTransactionService;

    public BankingServiceFactory(RevolutAccountService revAccountService, DeutscheAccountService dbAccountService,
                                 RevolutTransactionService revTransactionService, DeutscheTransactionService dbTransactionService) {
        this.revAccountService = revAccountService;
        this.dbAccountService = dbAccountService;
        this.revTransactionService = revTransactionService;
        this.dbTransactionService = dbTransactionService;
    }

    public AccountService retrieveAccountService(Bank bankingService) {
        logger.info("Fetching {} account service", bankingService);
        switch (bankingService) {
            case REVOLUT:
                return revAccountService;

            case DEUTSCHE:
                return dbAccountService;
        }
        throw new BankNotSupportedException("Should never happen since mapping prevents this");
    }

    public TransactionService retrieveTransactionService(Bank bankingService) {
        logger.info("Fetching {} transaction service", bankingService);
        switch (bankingService) {
            case REVOLUT:
                return revTransactionService;

            case DEUTSCHE:
                return dbTransactionService;
        }
        throw new BankNotSupportedException("Should never happen since mapping prevents this");
    }
}
