package com.j2020.service;

import com.j2020.model.TokenFetchException;

public interface TokenRenewalService {
    String getNewToken(String issuedToken) throws TokenFetchException;
}
