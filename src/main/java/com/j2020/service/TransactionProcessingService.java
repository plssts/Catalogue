/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.*;
import com.j2020.model.exception.BankNotSupportedException;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.service.jms.JmsTransactionProducer;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionProcessingService {
    private final BankingServiceFactory bankingService;
    private JmsTransactionProducer transactionProducer;
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessingService.class);

    public TransactionProcessingService(BankingServiceFactory bankingService,
                                        JmsTransactionProducer transactionProducer) {
        this.bankingService = bankingService;
        this.transactionProducer = transactionProducer;
    }

    public BatchOfPaymentsMessage initiatePaymentRequests(Map<String, List<GeneralPayment>> params) {
        if (params.isEmpty() || !Arrays.stream(params.keySet().toArray()).allMatch(bank -> EnumUtils.isValidEnum(Bank.class, bank.toString()))) {
            logger.error("Detected requests for bank services that are not supported");
            throw new BankNotSupportedException("Requested payments for services "
                    + Arrays.toString(params.keySet().toArray())
                    + " do not correspond to supported services of "
                    + Arrays.toString(Bank.values()));
        }

        List<GeneralPayment> merged = new ArrayList<>();
        params.forEach((bank, generalPayments) -> merged.addAll(generalPayments));

        return transactionProducer.sendPayments(merged);
    }

    public Map<String, List<GeneralTransaction>> collectTransactionResponse() {
        logger.info("Retrieving all transactions");

        Map<String, List<GeneralTransaction>> outcome = new HashMap<>();

        Stream.of(Bank.values()).forEach(bank -> {
            try {
                List<GeneralTransaction> transactions;
                if (bank.equals(Bank.DEUTSCHE)) {
                    transactions = bankingService.retrieveTransactionService(bank).retrieveTransactionData(
                            bankingService.retrieveAccountService(bank).retrieveAccountData().stream()
                                    .map(GeneralAccount::getAccountId).collect(Collectors.toList()));
                } else {
                    transactions = bankingService.retrieveTransactionService(bank).retrieveTransactionData(null);
                }
                outcome.put(bank.toString(), transactions);
            } catch (JsonProcessingException exception) {
                throw new JsonProcessingExceptionLambdaWrapper(exception.getMessage());
            }
        });

        return outcome;
    }
}
