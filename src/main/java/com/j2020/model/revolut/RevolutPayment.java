package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.Payment;

public class RevolutPayment implements Payment {
    @JsonProperty(value = "request_id")
    private String requestId;

    @JsonProperty(value = "account_id")
    private String accountId;

    private RevolutTransactionLegCounterparty receiver;
    private Float amount;
    private String currency;
    private String reference;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public RevolutTransactionLegCounterparty getReceiver() {
        return receiver;
    }

    public void setReceiver(RevolutTransactionLegCounterparty receiver) {
        this.receiver = receiver;
    }

    @Override
    public Float getAmount() {
        return amount;
    }

    @Override
    public void setIdentifyingInformation(String info) {
        this.requestId = info;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    /*
        Payment data

        {
          "request_id": "e0cbf84637264ee082a848b",
          "account_id": "bdab1c20-8d8c-430d-b967-87ac01af060c",
          "receiver": {
            "counterparty_id": "5138z40d1-05bb-49c0-b130-75e8cf2f7693",
            "account_id": "db7c73d3-b0df-4e0e-8a9a-f42aa99f52ab"
          },
          "amount": 123.11,
          "currency": "EUR",
          "reference": "Invoice payment #123"
        }
         */
}
