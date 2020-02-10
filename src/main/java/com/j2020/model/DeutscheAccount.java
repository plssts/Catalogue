package com.j2020.model;

public class DeutscheAccount implements Account {
    private String iban;
    private String currencyCode;
    private String bic;
    private String accountType;
    private Float currentBalance;
    private String productDescription;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
