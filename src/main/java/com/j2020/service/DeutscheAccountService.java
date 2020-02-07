/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DeutscheAccountService implements AccountService {
    private DeutscheTokenService tokenRenewal;

    private AccountRequestRetrievalService accountRetrieval;

    @Value("${deutscheAccount.accountUrl}")
    private String accountUrl;

    public DeutscheAccountService(DeutscheTokenService tokenRenewal,
                                  AccountRequestRetrievalService accountRetrieval,
                                  String accountUrl) {
        this.tokenRenewal = tokenRenewal;
        this.accountRetrieval = accountRetrieval;
        this.accountUrl = accountUrl;
    }

    @Override
    public String retrieveAccountData() {
        try {
            String accessToken = tokenRenewal.getToken();
            return accountRetrieval.processRequest(accessToken, accountUrl);

        } catch (HttpClientErrorException ex) {
            try {
                tokenRenewal.refreshToken();
                return "Access token has been refreshed. Reload this page.";

            } catch (TokenFetchException e) {
                return "Token negotiation failed. Application clearance might have expired";
            }
        }
    }

    public List<Account> retrieveSpecificAccount(String iban) {
        try {
            String accessToken = tokenRenewal.getToken();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(accountUrl)
                    .queryParam("iban", iban);
            return accountRetrieval.processRequest(accessToken, uriBuilder.toUriString());

        } catch (HttpClientErrorException ex) {
            try {
                tokenRenewal.refreshToken();
                return "Access token has been refreshed. Reload this page.";

            } catch (TokenFetchException e) {
                return "Token negotiation failed. Application clearance might have expired";
            }
        }
    }
}
