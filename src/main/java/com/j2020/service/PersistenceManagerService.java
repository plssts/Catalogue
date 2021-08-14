/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.*;
import com.j2020.repository.AccountRepository;
import com.j2020.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public PersistenceManagerService(TransactionProcessingService transactionService,
                                     AccountProcessingService accountService,
                                     AccountRepository accountRepository,
                                     TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Map<String, List<GeneralAccount>> returnAccounts() {
        logger.info("Getting all accounts from repository");

        Map<String, List<GeneralAccount>> outcome = new HashMap<>();
        Stream.of(Bank.values()).forEach(bank -> outcome.put(bank.toString(), accountRepository.findByBank(bank)));
        return outcome;
    }

    public Map<String, List<GeneralTransaction>> returnTransactions() {
        logger.info("Getting all transactions from repository");

        Map<String, List<GeneralTransaction>> outcome = new HashMap<>();
        Stream.of(Bank.values()).forEach(bank -> outcome.put(bank.toString(), transactionRepository.findByBank(bank)));
        return outcome;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public BatchOfPaymentsMessage processAndUpdateTransactions(Map<String, List<GeneralPayment>> params) {
        params.forEach((bank, generalPayments) -> generalPayments.forEach(payment -> payment.setBank(Bank.valueOf(bank))));

        BatchOfPaymentsMessage response = transactionService.initiatePaymentRequests(params);

        logger.info("Updating transaction repositories");
        Map<String, List<GeneralTransaction>> transactions = transactionService.collectTransactionResponse();
        params.forEach((bank, generalPayments) -> transactionRepository.saveAll(transactions.get(bank)));

        updateAccounts();

        return response;
    }

    private void updateAccounts() {
        logger.info("Fetching accounts for account repositories");

        Map<String, List<GeneralAccount>> accounts = accountService.collectAccountResponse();
        Stream.of(Bank.values()).forEach(bank -> accountRepository.saveAll(accounts.get(bank.toString())));
    }

    @PostConstruct
    private void init() {
        // TODO fix initialization
        //updateAccounts();
        //Map<String, List<GeneralTransaction>> transactions = transactionService.collectTransactionResponse();
        //Stream.of(Bank.values()).forEach(bank -> transactionRepository.saveAll(transactions.get(bank.toString())));
    }
}
