package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.GeneralPayment;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheMapperService;
import com.j2020.service.deutsche.DeutscheTokenService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTokenService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class DeutscheServicesTest {
    //@Mock
    private DeutscheTokenService tokenService;

    //@Mock
    private AccountRequestRetrievalService accountRetrieval;

    //@Mock
    private TransactionRequestRetrievalService transactionRetrieval;

    //@InjectMocks
    private DeutscheAccountService accountService;

    //@InjectMocks
    private DeutscheTransactionService transactionService;

    //@InjectMocks
    private DeutscheMapperService mapper;

    @Before
    public void setUp() {
        mapper = new DeutscheMapperService();
        tokenService = Mockito.mock(DeutscheTokenService.class);
        accountRetrieval = Mockito.mock(AccountRequestRetrievalService.class);
        accountService = new DeutscheAccountService(tokenService, accountRetrieval);

        setField(accountService, "accountUrl", "https://simulator-api.db.com/gw/dbapi/v1/cashAccounts");
    }

    @Test
    public void getAccountsNormalConditions() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<Account> accounts = generateAccounts();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheAccount.class);

        when(accountRetrieval.retrieveAccounts(
                ArgumentMatchers.anyString(),
                eq("https://simulator-api.db.com/gw/dbapi/v1/cashAccounts"),
                eq(type))).thenReturn(accounts);
        when(tokenService.getToken()).thenReturn("someToken");

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
        // FIXME |
        // FIXME |
        // FIXME |
    }

    @Test
    public void stripTrailingNullDecimals(){
        //
        // GIVEN
        //
        GeneralPayment general = generateValidGeneralPayment();
        Float specifiedAmount = general.getAmount();

        //
        // WHEN
        //
        DeutschePayment payment = mapper.toDeutschePayment(general);

        //
        // THEN
        //
        assertEquals("10.0", Float.toString(specifiedAmount));
        assertEquals("10", payment.getInstructedAmount().getAmount());
    }

    @Test
    public void retainActualDecimalPart(){
        //
        // GIVEN
        //
        GeneralPayment general = generateValidGeneralPayment();
        Float specifiedAmount = 10.01f;
        general.setAmount(specifiedAmount);

        //
        // WHEN
        //
        DeutschePayment payment = mapper.toDeutschePayment(general);

        //
        // THEN
        //
        assertEquals("10.01", Float.toString(specifiedAmount));
        assertEquals("10.01", payment.getInstructedAmount().getAmount());
    }

    private GeneralPayment generateValidGeneralPayment(){
        GeneralPayment payment = new GeneralPayment();
        payment.setAmount(10f);
        payment.setSourceAccount("anyValidAccount");
        payment.setDestinationAccount("anyValidAccount");
        payment.setCurrency("EUR");

        Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("creditorName", "Receiver");
        payment.setAdditionalInfo(additionalInfo);

        return payment;
    }

    private List<Account> generateAccounts() {
        List<Account> accounts = new ArrayList<>();

        DeutscheAccount demoResponseAccount = new DeutscheAccount();
        demoResponseAccount.setAccountId("DE0001");
        demoResponseAccount.setBic("BIC = code");
        demoResponseAccount.setCurrencyCode("EUR");
        demoResponseAccount.setCurrentBalance(80000f);
        demoResponseAccount.setAccountType("public");

        accounts.add(demoResponseAccount);

        return accounts;
    }
}
