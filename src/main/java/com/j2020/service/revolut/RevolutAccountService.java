/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.revolut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutAccountService implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(RevolutAccountService.class);
    private final RevolutTokenService tokenRenewal;
    private final AccountRequestRetrievalService accountRetrieval;
    private final RevolutMapperService revolutMapper;

    @Value("${revolutAccount.accountUrl}")
    private String accountUrl;

    public RevolutAccountService(RevolutTokenService tokenRenewal,
                                 AccountRequestRetrievalService accountRetrieval,
                                 RevolutMapperService revolutMapper) {
        this.tokenRenewal = tokenRenewal;
        this.accountRetrieval = accountRetrieval;
        this.revolutMapper = revolutMapper;
    }

    @Override
    public List<GeneralAccount> retrieveAccountData() throws JsonProcessingException {
        logger.info("Fetching Revolut accounts");
        String OAuthToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);

        List<Account> response = accountRetrieval.retrieveAccounts(OAuthToken, accountUrl, type);
        List<GeneralAccount> parsedAccounts = new ArrayList<>();

        logger.info("Constructing and validating Revolut accounts");
        response.forEach(account -> parsedAccounts.add(revolutMapper.toGeneralAccount((RevolutAccount) account)));

        return parsedAccounts;
    }

    @Override
    public boolean canProcessThisBank(Bank bankingService) {
        return bankingService.equals(Bank.REVOLUT);
    }
}
