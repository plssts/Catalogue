package com.j2020.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.revolut.RevolutAccount;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class RevolutServicesTest {
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
        ReflectionTestUtils.setField(RevolutAccountService.class, "accountUrl",
                "https://sandbox-b2b.revolut.com/api/1.0/accounts");
    }

    @Test
    public void getAccounts() {
        List<RevolutAccount> accounts = new ArrayList<>();
        RevolutAccount demoResponseAccountOne = new RevolutAccount("800", "savings", 500.1f,
                "EUR", "active", false, LocalDateTime.now().toString(), LocalDateTime.now().toString());

        RevolutAccount demoResponseAccountTwo = new RevolutAccount("801", "business", 3700f,
                "USD", "active", true, LocalDateTime.now().toString(), LocalDateTime.now().toString());

        accounts.add(demoResponseAccountOne);
        accounts.add(demoResponseAccountTwo);

        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);

        Mockito.doReturn(accounts).when(accountRetrieval.retrieveAccounts(
                ArgumentMatchers.anyString(),
                eq("https://sandbox-b2b.revolut.com/api/1.0/accounts"),
                eq(type)));

        List<? extends Account> actual = accountService.retrieveAccountData(Optional.empty());

        assertEquals(accounts, actual);
    }
}
