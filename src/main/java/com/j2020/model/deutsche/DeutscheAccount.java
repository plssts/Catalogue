/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.deutsche;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.Account;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class DeutscheAccount implements Account {
    @Id
    @JsonProperty(value = "iban")
    private String accountId;

    private String currencyCode;
    private String bic;
    private String accountType;
    private Float currentBalance;
    private String productDescription;

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeutscheAccount)) {
            return false;
        }
        DeutscheAccount that = (DeutscheAccount) other;
        return accountId.equals(that.accountId) &&
                currencyCode.equals(that.currencyCode) &&
                currentBalance.equals(that.currentBalance) &&
                productDescription.equals(that.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, currencyCode, currentBalance, productDescription);
    }
}
