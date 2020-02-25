/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.*;
import com.j2020.repository.AccountRepository;
import com.j2020.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class PersistenceManagerService {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceManagerService.class);
    private final TransactionProcessingService transactionService;
    private final AccountProcessingService accountService;
    private final RepositoryFactory repositoryFactory;

    @Autowired
    private AccountRepository accRep;

    @Autowired
    private TransactionRepository tRep;

    public PersistenceManagerService(TransactionProcessingService transactionService,
                                     RepositoryFactory repositoryFactory,
                                     AccountProcessingService accountService) {
        this.transactionService = transactionService;
        this.repositoryFactory = repositoryFactory;
        this.accountService = accountService;
    }

    public Map<String, List<GeneralAccount>> returnAccounts() {
        logger.info("Getting all accounts from repository");
        //List<GeneralAccount> revAccounts = repositoryFactory.retrieveAccountRepository(Bank.REVOLUT).findAll();
        List<GeneralAccount> revAccounts = accRep.findByBank(Bank.REVOLUT);
        List<GeneralAccount> dbAccounts = accRep.findByBank(Bank.DEUTSCHE);
        //List<GeneralAccount> dbAccounts = repositoryFactory.retrieveAccountRepository(Bank.DEUTSCHE).findAll();

        logger.info("{} Revolut; {} Deutsche Bank accounts", revAccounts.size(), dbAccounts.size());
        Map<String, List<GeneralAccount>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), revAccounts);
        outcome.put(Bank.DEUTSCHE.toString(), dbAccounts);

        return outcome;
    }

    public Map<String, List<Transaction>> returnTransactions() {
        logger.info("Getting all transactions from repository");
        List revTransactions = tRep.findByBank(Bank.REVOLUT);
        List dbTransactions = tRep.findByBank(Bank.DEUTSCHE);

        logger.info("{} Revolut; {} Deutsche Bank transactions", revTransactions.size(), dbTransactions.size());
        Map<String, List<Transaction>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), revTransactions);
        outcome.put(Bank.DEUTSCHE.toString(), dbTransactions);

        return outcome;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Map<String, List<PaymentResponse>> processAndUpdateTransactions(Map<String, List<GeneralPayment>> params) throws JsonProcessingException {
        Map<String, List<PaymentResponse>> response = transactionService.initiatePaymentRequests(params);

        logger.info("Updating following transaction repositories:");
        Map<String, List<GeneralTransaction>> transactions = transactionService.collectTransactionResponse();

        if (params.containsKey(Bank.REVOLUT.toString())) {
            logger.info("Updating Revolut transaction repository");
            repositoryFactory.retrieveTransactionRepository(Bank.REVOLUT).saveAll(transactions.get(Bank.REVOLUT.toString()));
        }
        if (params.containsKey(Bank.DEUTSCHE.toString())) {
            logger.info("Updating Deutsche Bank transaction repository");
            repositoryFactory.retrieveTransactionRepository(Bank.DEUTSCHE).saveAll(transactions.get(Bank.DEUTSCHE.toString()));
        }

        updateAccounts();

        return response;
    }

    private void updateAccounts() throws JsonProcessingException {
        logger.info("Fetching accounts for account repositories");
        Map<String, List<GeneralAccount>> accounts = accountService.collectAccountResponse();

        accounts.forEach((s, generalAccounts) -> System.out.println("Under " + s + "\n" + generalAccounts)); // FIXME remove

        logger.info("Updating Revolut account repository");
        repositoryFactory.retrieveAccountRepository(Bank.REVOLUT).saveAll(accounts.get(Bank.REVOLUT.toString()));
        logger.info("Updating Deutsche Bank account repository");
        repositoryFactory.retrieveAccountRepository(Bank.DEUTSCHE).saveAll(accounts.get(Bank.DEUTSCHE.toString()));
    }

    @PostConstruct
    private void init() throws JsonProcessingException {
        updateAccounts();
        Map<String, List<GeneralTransaction>> transactions = transactionService.collectTransactionResponse();

        repositoryFactory.retrieveTransactionRepository(Bank.REVOLUT).saveAll(transactions.get(Bank.REVOLUT.toString()));
        repositoryFactory.retrieveTransactionRepository(Bank.DEUTSCHE).saveAll(transactions.get(Bank.DEUTSCHE.toString()));
    }
}
