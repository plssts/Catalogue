/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.deutsche;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class DeutscheSepaPaymentAmount {
    //@Id
    private Long id;

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
