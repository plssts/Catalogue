/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeutscheAccountService implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(DeutscheAccountService.class);
    private final DeutscheTokenService tokenRenewal;
    private final AccountRequestRetrievalService accountRetrieval;
    private final DeutscheMapperService deutscheMapper;

    @Value("${deutscheAccount.accountUrl}")
    private String accountUrl;

    public DeutscheAccountService(DeutscheTokenService tokenRenewal,
                                  AccountRequestRetrievalService accountRetrieval,
                                  DeutscheMapperService deutscheMapper) {
        this.tokenRenewal = tokenRenewal;
        this.accountRetrieval = accountRetrieval;
        this.deutscheMapper = deutscheMapper;
    }

    @Override
    public List<GeneralAccount> retrieveAccountData() throws JsonProcessingException {
        logger.info("Fetching Deutsche Bank accounts");
        String accessToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheAccount.class);

        List<Account> response = accountRetrieval.retrieveAccounts(accessToken, accountUrl, type);
        List<GeneralAccount> parsedAccounts = new ArrayList<>();

        logger.info("Constructing and validating Deutsche Bank accounts");
        response.forEach(account -> parsedAccounts.add(deutscheMapper.toGeneralAccount((DeutscheAccount) account)));

        return parsedAccounts;
    }

    @Override
    public boolean canProcessThisBank(Bank bankingService) {
        return bankingService.equals(Bank.DEUTSCHE);
    }
}
