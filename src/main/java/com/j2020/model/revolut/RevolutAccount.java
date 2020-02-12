package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.Account;

public class RevolutAccount implements Account {
    @JsonProperty(value = "id")
    private String accountId;

    private String name;
    private Float balance;
    private String currency;
    private String state;

    @JsonProperty(value = "public")
    private Boolean isPublic;

    @JsonProperty(value = "created_at")
    private String dateOfCreating;

    @JsonProperty(value = "updated_at")
    private String dateOfUpdating;

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDateOfCreating() {
        return dateOfCreating;
    }

    public void setDateOfCreating(String dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }

    public String getDateOfUpdating() {
        return dateOfUpdating;
    }

    public void setDateOfUpdating(String dateOfUpdating) {
        this.dateOfUpdating = dateOfUpdating;
    }
}
