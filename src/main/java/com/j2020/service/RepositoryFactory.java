/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.Bank;
import com.j2020.model.BankNotSupportedException;
import com.j2020.repository.DeutscheAccountRepository;
import com.j2020.repository.DeutscheTransactionRepository;
import com.j2020.repository.RevolutAccountRepository;
import com.j2020.repository.RevolutTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RepositoryFactory {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryFactory.class);
    private final DeutscheAccountRepository deutscheAccountRepository;
    private final DeutscheTransactionRepository deutscheTransactionRepository;
    private final RevolutAccountRepository revolutAccountRepository;
    private final RevolutTransactionRepository revolutTransactionRepository;

    public RepositoryFactory(DeutscheAccountRepository deutscheAccountRepository,
                             DeutscheTransactionRepository deutscheTransactionRepository,
                             RevolutAccountRepository revolutAccountRepository,
                             RevolutTransactionRepository revolutTransactionRepository) {

        this.deutscheAccountRepository = deutscheAccountRepository;
        this.deutscheTransactionRepository = deutscheTransactionRepository;
        this.revolutAccountRepository = revolutAccountRepository;
        this.revolutTransactionRepository = revolutTransactionRepository;
    }

    public JpaRepository retrieveTransactionRepository(Bank bankingService) {
        logger.info("Fetching {} transaction repository", bankingService);
        switch (bankingService) {
            case REVOLUT:
                return revolutTransactionRepository;

            case DEUTSCHE:
                return deutscheTransactionRepository;
        }
        throw new BankNotSupportedException("Should never happen.");
    }

    public JpaRepository retrieveAccountRepository(Bank bankingService) {
        logger.info("Fetching {} account repository", bankingService);
        switch (bankingService) {
            case REVOLUT:
                return revolutAccountRepository;

            case DEUTSCHE:
                return deutscheAccountRepository;
        }
        throw new BankNotSupportedException("Should never happen.");
    }
}
