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
}
