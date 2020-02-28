/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.*;
import com.j2020.model.deutsche.*;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.model.exception.MissingPaymentRequestDataException;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.deutsche.*;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest(GoogleAuthenticator.class)
public class DeutscheServicesTest {
    private DeutscheTokenService tokenService;
    private AccountRequestRetrievalService accountRetrieval;
    private TransactionRequestRetrievalService transactionRetrieval;
    private DeutscheAccountService accountService;
    private DeutscheTransactionService transactionService;
    private DeutscheMapperService mapper;
    private RestTemplate restTemplate;
    private GoogleAuthenticator googleAuthenticator;
    private DeutscheMultiFactorService multiFactorService;

    @Before
    public void setUp() {
        mapper = new DeutscheMapperService();
        restTemplate = Mockito.mock(RestTemplate.class);
        googleAuthenticator = Mockito.mock(GoogleAuthenticator.class);
        tokenService = Mockito.mock(DeutscheTokenService.class);
        accountRetrieval = Mockito.mock(AccountRequestRetrievalService.class);
        accountService = new DeutscheAccountService(tokenService, accountRetrieval, mapper);
        transactionRetrieval = Mockito.mock(TransactionRequestRetrievalService.class);
        //transactionService = new DeutscheTransactionService(tokenService, transactionRetrieval, mapper);
        multiFactorService = new DeutscheMultiFactorService(googleAuthenticator, restTemplate);

        setField(accountService, "accountUrl", Constants.DEUTSCHE_ACCOUNT_URL);
        setField(transactionService, "transactionUrl", Constants.DEUTSCHE_TRANSACTION_URL);
        setField(transactionService, "paymentUrl", Constants.DEUTSCHE_PAYMENT_URL);
        setField(multiFactorService, "twoFactorSecret", Constants.DEUTSCHE_TWO_FACTOR_SECRET);
        setField(multiFactorService, "oneTimePassUrl", Constants.DEUTSCHE_ONE_TIME_PASS_URL);
    }

    @Test
    public void multifactorAppendsOtpProperly() {
        when(googleAuthenticator.getTotpPassword(anyString())).thenReturn(1);
        String actual = multiFactorService.getOneTimePass();
        assertEquals("000001", actual);
    }

    @Test
    public void prepareAuthorisationProperly() {
        // GIVEN
        DeutschePhototanResponse response = new DeutschePhototanResponse();
        response.setId("testedId");
        DeutscheOneTimePassword password = new DeutscheOneTimePassword();
        password.setOneTimePassword("testedValue");

        Map<String, String> expected = new HashMap<>();
        expected.put("otp", "testedValue");
        expected.put("idempotency-id", "testedId");

        // WHEN
        when(restTemplate.postForObject(anyString(), notNull(), eq(DeutschePhototanResponse.class))).thenReturn(response);
        when(restTemplate.patchForObject(anyString(), notNull(), eq(DeutscheOneTimePassword.class))).thenReturn(password);

        Map<String, String> actual = multiFactorService.prepareAuthorisation(
                Constants.TEST_ACCESS_TOKEN,
                Constants.TEST_ANY_ACCOUNT,
                Constants.TEST_CURRENCY_CODE,
                "0.0");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void throwsForWrongEntity() throws JsonProcessingException {
        // GIVEN
        List<String> ibans = new ArrayList<>();
        ibans.add(Constants.TEST_ANY_ACCOUNT);

        // WHEN
        when(transactionRetrieval.retrieveTransactions(
                anyString(),
                anyString(),
                any(JavaType.class))).thenThrow(JsonProcessingException.class);
        when(tokenService.getToken()).thenReturn("someToken");

        // THEN
        assertThrows(JsonProcessingExceptionLambdaWrapper.class, () -> transactionService.retrieveTransactionData(ibans));
    }

    @Test
    public void nullWithNoIbans() {
        // WHEN
        List<GeneralTransaction> actual = transactionService.retrieveTransactionData(null);

        // THEN
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    public void acknowledgeCorrectBankForProcessingAccounts() {
        // WHEN
        boolean actual = accountService.canProcessThisBank(Bank.DEUTSCHE);

        // THEN
        assertTrue(actual);
    }

    @Test
    public void acknowledgeCorrectBankForProcessingTransactions() {
        // WHEN
        boolean actual = transactionService.canProcessThisBank(Bank.DEUTSCHE);

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
        payments.add(TestDataHelper.generateValidGeneralPaymentForDeutsche());

        List<PaymentResponse> responses = new ArrayList<>();
        responses.add(TestDataHelper.generateDeutschePaymentResponse());

        JavaType type = new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class);

        // WHEN
        when(transactionRetrieval.pushPayments(
                anyString(),
                eq(Constants.DEUTSCHE_PAYMENT_URL),
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
        List<Account> accounts = TestDataHelper.generateDeutscheAccounts();
        List<GeneralAccount> parsedAccounts = accounts.stream()
                .map(account -> mapper.toGeneralAccount((DeutscheAccount) account))
                .collect(Collectors.toList());
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheAccount.class);

        when(accountRetrieval.retrieveAccounts(
                ArgumentMatchers.anyString(),
                eq(Constants.DEUTSCHE_ACCOUNT_URL),
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
        List<Transaction> transactions = TestDataHelper.generateDeutscheTransactions();
        List<String> dummyIban = new ArrayList<>();
        dummyIban.add(Constants.TEST_DEUTSCHE_DUMMY_SOURCE_IBAN);
        List<GeneralTransaction> parsedTransactions = transactions.stream()
                .map(transaction -> mapper.toGeneralTransaction((DeutscheTransaction) transaction))
                .collect(Collectors.toList());
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheTransaction.class);

        when(transactionRetrieval.retrieveTransactions(
                anyString(),
                anyString(),
                eq(type))).thenReturn(transactions);
        when(tokenService.getToken()).thenReturn("someToken");

        // WHEN
        List<GeneralTransaction> actual = transactionService.retrieveTransactionData(dummyIban);

        // THEN
        assertEquals(parsedTransactions, actual);
    }

    @Test
    public void stripTrailingNullDecimals() {
        // GIVEN
        GeneralPayment general = TestDataHelper.generateValidGeneralPaymentForDeutsche();
        Float specifiedAmount = general.getAmount();

        // WHEN
        DeutschePayment payment = mapper.toDeutschePayment(general);

        // THEN
        assertEquals("10.0", Float.toString(specifiedAmount));
        assertEquals("10", payment.getInstructedAmount().getAmount());
    }

    @Test
    public void retainActualDecimalPart() {
        // GIVEN
        GeneralPayment general = TestDataHelper.generateValidGeneralPaymentForDeutsche();
        Float specifiedAmount = 10.01f;
        general.setAmount(specifiedAmount);

        // WHEN
        DeutschePayment payment = mapper.toDeutschePayment(general);

        // THEN
        assertEquals("10.01", Float.toString(specifiedAmount));
        assertEquals("10.01", payment.getInstructedAmount().getAmount());
    }

    @Test
    public void mapIncompleteGeneralPayment() {
        // GIVEN
        GeneralPayment payment = TestDataHelper.generateInvalidGeneralPayment();

        // THEN
        assertThrows(MissingPaymentRequestDataException.class, () -> mapper.toDeutschePayment(payment));
    }
}
