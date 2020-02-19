package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTokenService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class DeutscheServicesTest {
    @Mock
    private RevolutTokenService tokenService;

    @Mock
    private AccountRequestRetrievalService accountRetrieval;

    @Mock
    private TransactionRequestRetrievalService transactionRetrieval;

    @InjectMocks
    private RevolutAccountService accountService;

    @InjectMocks
    private RevolutTransactionService transactionService;

    @BeforeTestExecution
    public void setUpAccountsUrl() {
        ReflectionTestUtils.setField(DeutscheAccountService.class, "accountUrl",
                "https://simulator-api.db.com/gw/dbapi/v1/cashAccounts");
    }

    @Test
    public void getAccountsNormalConditions() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<DeutscheAccount> accounts = generateAccounts();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheAccount.class);

        Mockito.doReturn(accounts).when(accountRetrieval.retrieveAccounts(
                ArgumentMatchers.anyString(),
                eq("https://sandbox-b2b.revolut.com/api/1.0/accounts"),
                eq(type)));

        //
        // WHEN
        //
        List<Account> actual = accountService.retrieveAccountData();

        //
        // THEN
        //
        assertEquals(accounts, actual);
    }

    @Test
    public void getTransactionsNormalConditions(){

    }

    private List<DeutscheAccount> generateAccounts() {
        List<DeutscheAccount> accounts = new ArrayList<>();

        DeutscheAccount demoResponseAccountOne = new DeutscheAccount();
        demoResponseAccountOne.setAccountId("DE0001");
        demoResponseAccountOne.setBic("BIC = code");
        demoResponseAccountOne.setCurrencyCode("EUR");
        demoResponseAccountOne.setCurrentBalance(80000f);
        demoResponseAccountOne.setAccountType("public");

        accounts.add(demoResponseAccountOne);

        return accounts;
    }
}
