package com.j2020.model.deutsche;

public class DeutscheSepaPaymentAccount {
    private String currencyCode;
    private String iban;

    public DeutscheSepaPaymentAccount() {

    }

    public DeutscheSepaPaymentAccount(String currencyCode, String iban) {
        this.currencyCode = currencyCode;
        this.iban = iban;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
