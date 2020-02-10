/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.TokenFetchException;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Optional;

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
            String accessToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheAccount.class);
            UriComponentsBuilder uriBuilder;

            if (specificAccount.isPresent()){
                uriBuilder = UriComponentsBuilder.fromUriString(accountUrl).queryParam("iban", specificAccount.get());
                return accountRetrieval.retrieveAccounts(accessToken, uriBuilder.toUriString(), type);
            } else {
                return accountRetrieval.retrieveAccounts(accessToken, accountUrl, type);
            }
        } catch (HttpClientErrorException ex) {
            throw new TokenFetchException();
        }
    }
}
