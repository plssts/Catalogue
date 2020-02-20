/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.GeneralPayment;
import com.j2020.model.MissingPaymentRequestDataException;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutMapperService;
import com.j2020.service.revolut.RevolutTokenService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.util.ReflectionTestUtils;
//import sun.java2d.loops.FillRect;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class RevolutServicesTest {
    //@Mock
    private RevolutTokenService tokenService;

    //@Mock
    private AccountRequestRetrievalService accountRetrieval;

    //@Mock
    private TransactionRequestRetrievalService transactionRetrieval;

    //@InjectMocks
    private RevolutAccountService accountService;

    //@InjectMocks
    private RevolutTransactionService transactionService;

    //@InjectMocks
    private RevolutMapperService mapper;

    @Before
    public void setUp() {
        mapper = new RevolutMapperService();
        tokenService = Mockito.mock(RevolutTokenService.class);
        accountRetrieval = Mockito.mock(AccountRequestRetrievalService.class);
        accountService = new RevolutAccountService(tokenService, accountRetrieval);

        setField(accountService, "accountUrl", "https://sandbox-b2b.revolut.com/api/1.0/accounts");
    }

    @Test
    public void getAccountsNormalConditions() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<Account> accounts = generateRevolutAccounts();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);

        when(accountRetrieval.retrieveAccounts(anyString(), eq("https://sandbox-b2b.revolut.com/api/1.0/accounts"), eq(type))).thenReturn(accounts);
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
    public void mapIncompleteGeneralPayment(){
        //
        // GIVEN
        //
        GeneralPayment payment = generateInvalidGeneralPayment();

        //
        // THEN
        //
        assertThrows(MissingPaymentRequestDataException.class, () -> mapper.toRevolutPayment(payment));
    }

    private GeneralPayment generateInvalidGeneralPayment(){
        GeneralPayment payment = new GeneralPayment();
        payment.setAmount(100f);
        payment.setSourceAccount("anyValidAccount");
        payment.setDestinationAccount("anyValidAccount");
        payment.setCurrency("EUR");

        return payment;
    }

    private List<Account> generateRevolutAccounts() {
        List<Account> accounts = new ArrayList<>();

        RevolutAccount demoResponseAccountOne = new RevolutAccount();
        demoResponseAccountOne.setAccountId("800");
        demoResponseAccountOne.setName("savings");
        demoResponseAccountOne.setBalance(500.1f);
        demoResponseAccountOne.setCurrency("EUR");
        demoResponseAccountOne.setState("active");
        demoResponseAccountOne.setPublic(false);
        demoResponseAccountOne.setDateOfCreating(LocalDateTime.now().toString());
        demoResponseAccountOne.setDateOfUpdating(LocalDateTime.now().toString());

        RevolutAccount demoResponseAccountTwo = new RevolutAccount();
        demoResponseAccountTwo.setAccountId("801");
        demoResponseAccountTwo.setName("business");
        demoResponseAccountTwo.setBalance(3700f);
        demoResponseAccountTwo.setCurrency("EUR");
        demoResponseAccountTwo.setState("active");
        demoResponseAccountTwo.setPublic(true);
        demoResponseAccountTwo.setDateOfCreating(LocalDateTime.now().toString());
        demoResponseAccountTwo.setDateOfUpdating(LocalDateTime.now().toString());

        accounts.add(demoResponseAccountOne);
        accounts.add(demoResponseAccountTwo);

        return accounts;
    }
}
