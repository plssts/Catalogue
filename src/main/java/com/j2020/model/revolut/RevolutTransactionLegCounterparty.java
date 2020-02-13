package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutTransactionLegCounterparty {
    @JsonProperty(required = false)
    private String id;

    @JsonProperty(value = "account_type")
    private String accountType;

    @JsonProperty(value = "account_id")
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
