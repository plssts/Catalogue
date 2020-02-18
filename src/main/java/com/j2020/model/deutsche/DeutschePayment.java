/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.deutsche;

import com.j2020.model.Payment;

public class DeutschePayment implements Payment {
    private DeutscheSepaPaymentAccount debtorAccount;
    private DeutscheSepaPaymentAmount instructedAmount;
    private String creditorName;
    private DeutscheSepaPaymentAccount creditorAccount;

    @Override
    public Float getAmount() {
        return Float.valueOf(instructedAmount.getAmount());
    }

    @Override
    public void setIdentifyingInformation(String info) {
        creditorName = info;
    }

    public DeutscheSepaPaymentAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(DeutscheSepaPaymentAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public DeutscheSepaPaymentAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(DeutscheSepaPaymentAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public DeutscheSepaPaymentAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(DeutscheSepaPaymentAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }
}
