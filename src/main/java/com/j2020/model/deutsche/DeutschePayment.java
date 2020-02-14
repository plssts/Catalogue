package com.j2020.model.deutsche;

import com.j2020.model.Payment;

public class DeutschePayment implements Payment {
    /*
    "{\n" +
        "  \"debtorAccount\": {\n" +
        "    \"currencyCode\": \"EUR\",\n" +
        "    \"iban\": \"DE10010000000000005772\"\n" +
        "  },\n" +
        "  \"instructedAmount\": {\n" +
        "    \"amount\": \"1\",\n" +
        "    \"currencyCode\": \"EUR\"\n" +
        "  },\n" +
        "  \"creditorName\": \"Test Name\",\n" +
        "  \"creditorAccount\": {\n" +
        "    \"currencyCode\": \"EUR\",\n" +
        "    \"iban\": \"DE10010000000000005771\"\n" +
        "  }\n" +
        "}";
     */

    @Override
    public Float getAmount() {
        return null;
    }

    @Override
    public void setIdentifyingInformation(String info) {
        // TODO to creditor name
    }
}
