package com.j2020.service;

import com.j2020.model.Account;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<? extends Transaction> retrieveTransactionData(Optional<List<String>> accountIds);
    List<? extends PaymentResponse> createPayments(List<? extends Payment> payments);
    String demo();
}
