/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.revolut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevolutAccountService implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(RevolutAccountService.class);
    private final RevolutTokenService tokenRenewal;
    private final AccountRequestRetrievalService accountRetrieval;

    @Value("${revolutAccount.accountUrl}")
    private String accountUrl;

    public RevolutAccountService(RevolutTokenService tokenRenewal,
                                 AccountRequestRetrievalService accountRetrieval) {
        this.tokenRenewal = tokenRenewal;
        this.accountRetrieval = accountRetrieval;
    }

    @Override
    public List<Account> retrieveAccountData() throws JsonProcessingException {
        logger.info("Fetching Revolut accounts");
        String OAuthToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);

        return accountRetrieval.retrieveAccounts(OAuthToken, accountUrl, type);
    }
}
