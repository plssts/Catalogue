package com.j2020.service;

import com.j2020.model.Bank;
import com.j2020.model.GeneralPayment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.revolut.RevolutPaymentResponse;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionProcessingService {
    private BankingServiceFactory bankingService;

    public TransactionProcessingService(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    public Map<Bank, List<PaymentResponse>> initiatePaymentRequests(Map<Bank, List<GeneralPayment>> params) {
        List<PaymentResponse> revolutResponses = bankingService.retrieveTransactionService(Bank.REVOLUT).createPayments(params.get(Bank.REVOLUT));
        List<PaymentResponse> deutscheResponses = bankingService.retrieveTransactionService(Bank.DEUTSCHE).createPayments(params.get(Bank.DEUTSCHE));

        Map<Bank, List<PaymentResponse>> outcome = new EnumMap<>(Bank.class);
        outcome.put(Bank.REVOLUT, revolutResponses);
        outcome.put(Bank.DEUTSCHE, deutscheResponses);

        return outcome;
    }
}
