/**
 * @author Paulius Staisiunas
 */

package com.j2020.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class GeneralAccount {
    @Id
    private String accountId;

    private Float balance;
    private String currency;
    private String type;
    private String description;
    private Bank bank;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GeneralAccount{" +
                "accountId='" + accountId + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", bank=" + bank +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeneralAccount)) {
            return false;
        }
        GeneralAccount that = (GeneralAccount) other;
        return accountId.equals(that.accountId) &&
                balance.equals(that.balance) &&
                currency.equals(that.currency) &&
                bank == that.bank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance, currency, type, description, bank);
    }
}
