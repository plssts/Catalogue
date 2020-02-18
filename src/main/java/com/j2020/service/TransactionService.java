/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.GeneralPayment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> retrieveTransactionData(List<String> accountIds) throws JsonProcessingException;

    List<PaymentResponse> createPayments(List<GeneralPayment> payments) throws JsonProcessingException;
}
