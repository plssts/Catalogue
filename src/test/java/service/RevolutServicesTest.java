/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.*;
import com.j2020.model.exception.MissingPaymentRequestDataException;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutPaymentResponse;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutMapperService;
import com.j2020.service.revolut.RevolutTokenService;
import com.j2020.service.revolut.RevolutTransactionService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@Ignore
public class RevolutServicesTest {
    private RevolutTokenService tokenService;
    private AccountRequestRetrievalService accountRetrieval;
    private TransactionRequestRetrievalService transactionRetrieval;
    private RevolutAccountService accountService;
    private RevolutTransactionService transactionService;
    private RevolutMapperService mapper;

    @Before
    public void setUp() {
        mapper = new RevolutMapperService();
        tokenService = Mockito.mock(RevolutTokenService.class);
        accountRetrieval = Mockito.mock(AccountRequestRetrievalService.class);
        accountService = new RevolutAccountService(tokenService, accountRetrieval, mapper);
        transactionRetrieval = Mockito.mock(TransactionRequestRetrievalService.class);
        //transactionService = new RevolutTransactionService(tokenService, transactionRetrieval, mapper);

        setField(accountService, "accountUrl", Constants.REVOLUT_ACCOUNT_URL);
        setField(transactionService, "transactionUrl", Constants.REVOLUT_TRANSACTION_URL);
        setField(transactionService, "paymentUrl", Constants.REVOLUT_PAYMENT_URL);
    }

    @Test
    public void acknowledgeCorrectBankForProcessingAccounts() {
        // WHEN
        boolean actual = accountService.canProcessThisBank(Bank.REVOLUT);

        // THEN
        assertTrue(actual);
    }

    @Test
    public void acknowledgeCorrectBankForProcessingTransactions() {
        // WHEN
        boolean actual = transactionService.canProcessThisBank(Bank.REVOLUT);

        // THEN
        assertTrue(actual);
    }

    @Test
    public void returnWithNullPayments() throws JsonProcessingException {
        // WHEN
        List<PaymentResponse> actual = transactionService.createPayments(null);

        // THEN
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    public void createAndValidatePayments() throws JsonProcessingException {
        // GIVEN
        List<GeneralPayment> payments = new ArrayList<>();
        payments.add(TestDataHelper.generateValidGeneralPaymentForRevolut());

        List<PaymentResponse> responses = new ArrayList<>();
        responses.add(TestDataHelper.generateRevolutPaymentResponse());

        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class);

        // WHEN
        when(transactionRetrieval.pushPayments(
                anyString(),
                eq(Constants.REVOLUT_PAYMENT_URL),
                anyList(),
                eq(type))).thenReturn(responses);
        when(tokenService.getToken()).thenReturn("someToken");

        List<PaymentResponse> actual = transactionService.createPayments(payments);

        // THEN
        assertEquals(responses, actual);
    }

    @Test
    public void getAccountsNormalConditions() throws JsonProcessingException {
        // GIVEN
        List<Account> accounts = TestDataHelper.generateRevolutAccounts();
        List<GeneralAccount> parsedAccounts = accounts.stream()
                .map(account -> mapper.toGeneralAccount((RevolutAccount) account))
                .collect(Collectors.toList());
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);

        when(accountRetrieval.retrieveAccounts(
                anyString(),
                eq(Constants.REVOLUT_ACCOUNT_URL),
                eq(type))).thenReturn(accounts);
        when(tokenService.getToken()).thenReturn("someToken");

        // WHEN
        List<GeneralAccount> actual = accountService.retrieveAccountData();

        // THEN
        assertEquals(parsedAccounts, actual);
    }

    @Test
    public void getTransactionsNormalConditions() throws JsonProcessingException {
        // GIVEN
        List<Transaction> transactions = TestDataHelper.generateRevolutTransactions();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutTransaction.class);
        List<GeneralTransaction> parsedTransactions = transactions.stream()
                .map(transaction -> mapper.toGeneralTransaction((RevolutTransaction) transaction))
                .collect(Collectors.toList());

        when(transactionRetrieval.retrieveTransactions(
                ArgumentMatchers.anyString(),
                eq(Constants.REVOLUT_TRANSACTION_URL),
                eq(type))).thenReturn(transactions);
        when(tokenService.getToken()).thenReturn("someToken");

        // WHEN
        List<GeneralTransaction> actual = transactionService.retrieveTransactionData(null);

        // THEN
        assertEquals(parsedTransactions, actual);
    }

    @Test
    public void mapIncompleteGeneralPayment() {
        // GIVEN
        GeneralPayment payment = TestDataHelper.generateInvalidGeneralPayment();

        // THEN
        assertThrows(MissingPaymentRequestDataException.class, () -> mapper.toRevolutPayment(payment));
    }

    @Test
    public void mapCompleteGeneralPayment() {
        // GIVEN
        GeneralPayment payment = TestDataHelper.generateInvalidGeneralPayment();
        Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("reference", "someReference");
        additionalInfo.put("counterparty", "testedValue");
        payment.setAdditionalInfo(additionalInfo);

        // WHEN
        RevolutPayment revolutPayment = mapper.toRevolutPayment(payment);

        // THEN
        assertEquals("testedValue", revolutPayment.getReceiver().getCounterpartyId());
    }
}
