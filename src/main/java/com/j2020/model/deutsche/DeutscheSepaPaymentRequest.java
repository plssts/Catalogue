package com.j2020.model.deutsche;

public class DeutscheSepaPaymentRequest {
    private String method = "PHOTOTAN";
    private String requestType = "INSTANT_SEPA_CREDIT_TRANSFERS";
    private DeutscheSepaPaymentRequestData requestData;
    private String language = "de";

    public DeutscheSepaPaymentRequest(){

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
