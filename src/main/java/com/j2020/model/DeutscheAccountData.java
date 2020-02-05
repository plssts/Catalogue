package com.j2020.model;

import lombok.Data;

@Data
public class DeutscheAccountData {
    private String iban;
    private String currencyCode;
    private String bic;
    private String accountType;
    private Float currentBalance;
    private String productDescription;
}
