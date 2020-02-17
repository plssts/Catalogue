package com.j2020.model.deutsche;

import javax.validation.constraints.NotNull;

public class DeutscheSepaPaymentAmount {
    @NotNull(message = "Payment amount is missing")
    private String amount;

    @NotNull(message = "Currency code is missing")
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
