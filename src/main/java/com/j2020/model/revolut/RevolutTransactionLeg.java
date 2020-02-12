package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutTransactionLeg {
    @JsonProperty(value = "leg_id")
    private String legId;

    @JsonProperty(value = "account_id")
    private String accountId;

    @JsonProperty(required = false)
    private RevolutTransactionLegCounterparty counterparty;

    private Float amount;
    private String currency;
    private String description;

    @JsonProperty(required = false)
    private Float balance;

    public String getLegId() {
        return legId;
    }

    public void setLegId(String legId) {
        this.legId = legId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public RevolutTransactionLegCounterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(RevolutTransactionLegCounterparty counterparty) {
        this.counterparty = counterparty;
    }

    public Float getAmount() {
        return amount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}