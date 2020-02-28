/**
 * @author Paulius Staisiunas
 */

package com.j2020.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class GeneralTransaction {
    @Id
    private String transactionId;

    private String origin;
    private String creditor;
    private Float amount;
    private String type;
    private String state;
    private String requestId;
    private String reference;
    private String createdAt;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeneralTransaction)) {
            return false;
        }
        GeneralTransaction that = (GeneralTransaction) other;
        return transactionId.equals(that.transactionId) &&
                origin.equals(that.origin) &&
                creditor.equals(that.creditor) &&
                amount.equals(that.amount) &&
                bank == that.bank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, origin, creditor, amount, createdAt, bank);
    }
}
