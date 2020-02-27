/**
 * @author Paulius Staisiunas
 */

package com.j2020.model;

import java.util.Map;

public class GeneralPayment {
    private String sourceAccount;
    private String destinationAccount;
    private String currency;
    private Float amount;
    private Map<String, String> additionalInfo;
    private BatchOfPayments batch;
    private Long bopid;
    private Bank bank;

    public BatchOfPayments getBatch() {
        return batch;
    }

    public void setBatch(BatchOfPayments batch) {
        this.batch = batch;
    }

    public Long getBopid() {
        return bopid;
    }

    public void setBopid(Long bopid) {
        this.bopid = bopid;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public BatchOfPayments getBatchOfPayments() {
        return batch;
    }

    public void setBatchOfPayments(BatchOfPayments batch) {
        this.batch = batch;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Map<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Map<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "GeneralPayment{" +
                "sourceAccount='" + sourceAccount + '\'' +
                ", destinationAccount='" + destinationAccount + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", additionalInfo=" + additionalInfo +
                ", batchId=" + batch.getId() +
                ", bank=" + bank +
                '}';
    }
}
