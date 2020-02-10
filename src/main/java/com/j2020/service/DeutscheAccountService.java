/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.DeutscheAccount;
import com.j2020.model.RevolutTokenRenewalResponse;
import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Optional;

import com.j2020.model.DeutscheAccount;

@Service
public class DeutscheAccountService implements AccountService {
    private DeutscheTokenService tokenRenewal;

    private AccountRequestRetrievalService accountRetrieval;

    @Value("${deutscheAccount.accountUrl}")
    private String accountUrl;

    public DeutscheAccountService(DeutscheTokenService tokenRenewal,
                                  AccountRequestRetrievalService accountRetrieval) {
        this.tokenRenewal = tokenRenewal;
        this.accountRetrieval = accountRetrieval;
    }

    @Override
    public List<? extends Account> retrieveAccountData(Optional<String> specificAccount) {
        try {
            System.out.println("Deutshce service retrieving");
            String accessToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheAccount.class);
            UriComponentsBuilder uriBuilder;

            System.out.println("Deutsche checking presence");
            if (specificAccount.isPresent()){
                System.out.println("specific: " + specificAccount.get());
                uriBuilder = UriComponentsBuilder.fromUriString(accountUrl).queryParam("iban", specificAccount.get());
                return accountRetrieval.retrieveAccounts(accessToken, uriBuilder.toUriString(), type);
            } else {
                System.out.println("all accounts please");
                return accountRetrieval.retrieveAccounts(accessToken, accountUrl, type);
            }
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException();
        }
    }
}
