/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.Bank;
import com.j2020.model.BankNotSupportedException;
import com.j2020.model.Transaction;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankingServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(BankingServiceFactory.class);

    @Autowired
    private List<AccountService> accountServices;

    @Autowired
    private List<TransactionService> transactionServices;

    public BankingServiceFactory(List<AccountService> accountServices, List<TransactionService> transactionServices) {
        this.accountServices = accountServices;
        this.transactionServices = transactionServices;
    }

    public AccountService retrieveAccountService(Bank bankingService) {
        logger.info("Fetching {} account service", bankingService);

        // FIXME do it like this
        return accountServices.stream()
                .filter(service -> service.canProcessThisBank(bankingService))
                .findAny()
                .orElseThrow(() -> new BankNotSupportedException("Should never happen since mapping prevents this"));

        /*switch (bankingService) {
            case REVOLUT:
                return revAccountService;

            case DEUTSCHE:
                return dbAccountService;
        }
        throw new BankNotSupportedException("Should never happen since mapping prevents this");*/
    }

    public TransactionService retrieveTransactionService(Bank bankingService) {
        logger.info("Fetching {} transaction service", bankingService);

        return transactionServices.stream()
                .filter(service -> service.canProcessThisBank(bankingService))
                .findAny()
                .orElseThrow(() -> new BankNotSupportedException("Should never happen since mapping prevents this"));

        /*switch (bankingService) {
            case REVOLUT:
                return revTransactionService;

            case DEUTSCHE:
                return dbTransactionService;
        }
        throw new BankNotSupportedException("Should never happen since mapping prevents this");*/
    }
}
