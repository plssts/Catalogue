package com.j2020.service;

import com.j2020.model.Bank;
import com.j2020.model.BankNotSupportedException;
import com.j2020.model.GeneralPayment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.revolut.RevolutPaymentResponse;
import com.j2020.service.deutsche.DeutscheTransactionService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionProcessingService {
    private BankingServiceFactory bankingService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessingService.class);

    public TransactionProcessingService(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    public Map<String, List<PaymentResponse>> initiatePaymentRequests(Map<String, List<GeneralPayment>> params) {
        System.out.println(Arrays.toString(Bank.values()) + " contains " + params.keySet().toString());

        if (!Arrays.stream(params.keySet().toArray()).allMatch(bank -> EnumUtils.isValidEnum(Bank.class, bank.toString()))) {
            throw new BankNotSupportedException("Requested payments for services " + Arrays.toString(params.keySet().toArray()) + " do not correspond to supported services of " + Arrays.toString(Bank.values()));
        }

        List<PaymentResponse> revolutResponses = new ArrayList<>();
        List<PaymentResponse> deutscheResponses = new ArrayList<>();

        if (params.containsKey(Bank.REVOLUT.toString())){
            revolutResponses = bankingService.retrieveTransactionService(Bank.REVOLUT).createPayments(params.get(Bank.REVOLUT.toString()));
        }
        if (params.containsKey(Bank.DEUTSCHE.toString())){
            deutscheResponses = bankingService.retrieveTransactionService(Bank.DEUTSCHE).createPayments(params.get(Bank.DEUTSCHE.toString()));
        }

        Map<String, List<PaymentResponse>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), revolutResponses);
        outcome.put(Bank.DEUTSCHE.toString(), deutscheResponses);

        return outcome;
    }
}
