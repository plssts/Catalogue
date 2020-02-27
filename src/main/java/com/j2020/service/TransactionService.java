/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Bank;
import com.j2020.model.GeneralPayment;
import com.j2020.model.GeneralTransaction;
import com.j2020.model.PaymentResponse;

import java.util.List;

public interface TransactionService {
    List<GeneralTransaction> retrieveTransactionData(List<String> accountIds) throws JsonProcessingException;

    List<PaymentResponse> createPayments(List<GeneralPayment> payments) throws JsonProcessingException;

    boolean canProcessThisBank(Bank bankingService);
}
