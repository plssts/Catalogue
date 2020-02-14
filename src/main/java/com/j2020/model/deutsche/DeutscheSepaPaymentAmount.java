package com.j2020.model.deutsche;

public class DeutscheSepaPaymentAmount {
    private String amount;
    private String currencyCode;

    public DeutscheSepaPaymentAmount() {

    }

    public DeutscheSepaPaymentAmount(String amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
