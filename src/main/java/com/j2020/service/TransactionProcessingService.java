/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.*;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionProcessingService {
    private final BankingServiceFactory bankingService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessingService.class);

    public TransactionProcessingService(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    public Map<String, List<PaymentResponse>> initiatePaymentRequests(Map<String, List<GeneralPayment>> params) throws JsonProcessingException {
        if (params.isEmpty() || !Arrays.stream(params.keySet().toArray()).allMatch(bank -> EnumUtils.isValidEnum(Bank.class, bank.toString()))) {
            logger.error("Detected requests for bank services that are not supported");
            throw new BankNotSupportedException("Requested payments for services "
                    + Arrays.toString(params.keySet().toArray())
                    + " do not correspond to supported services of "
                    + Arrays.toString(Bank.values()));
        }

        List<PaymentResponse> revolutResponses = new ArrayList<>();
        List<PaymentResponse> deutscheResponses = new ArrayList<>();

        if (params.containsKey(Bank.REVOLUT.toString())) {
            logger.info("Processing Revolut transactions");
            revolutResponses = bankingService.retrieveTransactionService(Bank.REVOLUT)
                    .createPayments(params.get(Bank.REVOLUT.toString()));
        }
        if (params.containsKey(Bank.DEUTSCHE.toString())) {
            logger.info("Processing Deutsche Bank transactions");
            deutscheResponses = bankingService.retrieveTransactionService(Bank.DEUTSCHE)
                    .createPayments(params.get(Bank.DEUTSCHE.toString()));
        }

        Map<String, List<PaymentResponse>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), revolutResponses);
        outcome.put(Bank.DEUTSCHE.toString(), deutscheResponses);

        return outcome;
    }

    public Map<String, List<GeneralTransaction>> collectTransactionResponse() throws JsonProcessingException {
        logger.info("Retrieving Revolut transactions");
        List<GeneralTransaction> transactionsRevo = bankingService.retrieveTransactionService(Bank.REVOLUT)
                .retrieveTransactionData(null);

        logger.info("Collecting Deutsche Bank accounts and corresponding transactions");
        List<String> ibans = bankingService.retrieveAccountService(Bank.DEUTSCHE)
                .retrieveAccountData().stream()
                .map(GeneralAccount::getAccountId).collect(Collectors.toList());
        List<GeneralTransaction> transactionsDeut = bankingService.retrieveTransactionService(Bank.DEUTSCHE)
                .retrieveTransactionData(ibans);

        Map<String, List<GeneralTransaction>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), transactionsRevo);
        outcome.put(Bank.DEUTSCHE.toString(), transactionsDeut);

        return outcome;
    }
}
