/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import com.j2020.model.JsonProcessingExceptionLambdaWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class AccountProcessingService {
    private final BankingServiceFactory bankingService;
    private static final Logger logger = LoggerFactory.getLogger(AccountProcessingService.class);

    public AccountProcessingService(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    public Map<String, List<GeneralAccount>> collectAccountResponse() {
        logger.info("Retrieving account entries");

        Map<String, List<GeneralAccount>> outcome = new HashMap<>();
        Stream.of(Bank.values()).forEach(bank -> {
            try {
                outcome.put(bank.toString(), bankingService.retrieveAccountService(bank).retrieveAccountData());
            } catch (JsonProcessingException exception) {
                throw new JsonProcessingExceptionLambdaWrapper(exception.getMessage());
            }
        });

        return outcome;
    }
}
