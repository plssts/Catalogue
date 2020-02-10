/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.RevolutAccount;
import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class RevolutAccountService implements AccountService {
    private RevolutTokenService tokenRenewal;
    private AccountRequestRetrievalService accountRetrieval;

    @Value("${revolutAccount.accountUrl}")
    private String accountUrl;

    public RevolutAccountService(RevolutTokenService tokenRenewal,
                                 AccountRequestRetrievalService accountRetrieval) {
        this.tokenRenewal = tokenRenewal;
        this.accountRetrieval = accountRetrieval;
    }

    @Override
    public List<? extends Account> retrieveAccountData(Optional<String> specificAccount){
        try {
            String OAuthToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);
            UriComponentsBuilder uriBuilder;

            System.out.println("gonna check if anything is present");
            if (specificAccount.isPresent()){
                System.out.println("needed specific amount: " + specificAccount.toString());
                uriBuilder = UriComponentsBuilder.fromUriString(accountUrl).pathSegment(specificAccount.get());

                return accountRetrieval.retrieveAccounts(OAuthToken, uriBuilder.toUriString(), type);
            } else {
                return accountRetrieval.retrieveAccounts(OAuthToken, accountUrl, type);
            }
        } catch (HttpClientErrorException ex){
            throw new RuntimeException();
        }
    }
}
