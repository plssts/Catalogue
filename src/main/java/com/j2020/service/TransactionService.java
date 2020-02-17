package com.j2020.service;

import com.j2020.model.GeneralPayment;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> retrieveTransactionData(Optional<List<String>> accountIds);
    List<PaymentResponse> createPayments(List<GeneralPayment> payments);
}
