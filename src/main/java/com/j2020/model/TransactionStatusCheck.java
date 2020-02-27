package com.j2020.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TransactionStatusCheck {
    @Id
    @JsonAlias(value = "id")
    private String paymentId;

    @JsonAlias(value = "state")
    private String transactionStatus;

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "bop_id", nullable = false)
    //private BatchOfPayments batch;
    private Bank bank;
    private String sourceAccount;
    private String destinationAccount;

    @JsonIgnore
    private Long bopid;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public Long getBopid() {
        return bopid;
    }

    public void setBopid(Long bopid) {
        this.bopid = bopid;
    }

    /*public BatchOfPayments getBatch() {
        return batch;
    }

    public void setBatch(BatchOfPayments batch) {
        this.batch = batch;
    }*/

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
