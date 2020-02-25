/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.*;

import java.util.List;

public interface TransactionService {
    List<GeneralTransaction> retrieveTransactionData(List<String> accountIds) throws JsonProcessingException;

    List<PaymentResponse> createPayments(List<GeneralPayment> payments) throws JsonProcessingException;

    boolean canProcessThisBank(Bank bankingService);
}
