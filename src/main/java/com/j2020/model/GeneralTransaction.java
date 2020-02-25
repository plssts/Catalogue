package com.j2020.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GeneralTransaction {
    @Id
    private String transactionId; // REVO: id; DB: paymentIdentification
    private String origin; // REVO: legs->accountId DB: originIban
    private String creditor; // REVO: legs->counterparty->accountId; DB: creditorId / counterpartyIban (whichever is nonnull)
    private Float amount; // REVO: legs->amount; DB: amount
    private String type; // REVO: type; DB:
    private String state; // REVO: state; DB:
    private String requestId; // REVO: requestId; DB: e2eReference
    private String reference; // REVO: reference; DB: paymentReference
    private String createdAt; // REVO: created_at; DB: bookingDate
    private Bank bank;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
