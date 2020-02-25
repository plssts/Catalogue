/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.deutsche;

import com.j2020.Constants;

public class DeutscheSepaPaymentRequest {
    private String method = Constants.DEUTSCHE_SEPA_PAYMENT_REQUEST_METHOD;
    private String requestType = Constants.DEUTSCHE_SEPA_PAYMENT_REQUEST_TYPE;
    private DeutscheSepaPaymentRequestData requestData;
    private String language = Constants.DEUTSCHE_SEPA_PAYMENT_REQUEST_LANGUAGE;

    public DeutscheSepaPaymentRequest() {

    }

    public DeutscheSepaPaymentRequest(String iban, String currency, String amount) {
        requestData = new DeutscheSepaPaymentRequestData(iban, currency, amount);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public DeutscheSepaPaymentRequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(DeutscheSepaPaymentRequestData requestData) {
        this.requestData = requestData;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
