/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
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
import helper.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

// FIXME move strings to @Value
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
        accountService = new RevolutAccountService(tokenService, accountRetrieval);
        transactionRetrieval = Mockito.mock(TransactionRequestRetrievalService.class);
        transactionService = new RevolutTransactionService(tokenService, transactionRetrieval, mapper);

        setField(accountService, "accountUrl", "https://sandbox-b2b.revolut.com/api/1.0/accounts");

        setField(transactionService, "transactionUrl", "https://sandbox-b2b.revolut.com/api/1.0/transactions");
        setField(transactionService, "paymentUrl", "https://sandbox-b2b.revolut.com/api/1.0/pay");
    }

    @Test
    public void returnWithNullPayments() throws JsonProcessingException {
        //
        // WHEN
        //
        List<PaymentResponse> actual = transactionService.createPayments(null);

        //
        // THEN
        //
        assertIterableEquals(new ArrayList<>(), actual);
    }

    @Test
    public void createAndValidatePayments() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<GeneralPayment> payments = new ArrayList<>();
        payments.add(TestDataGenerator.generateValidGeneralPaymentForRevolut());

        List<PaymentResponse> responses = new ArrayList<>();
        responses.add(TestDataGenerator.generateRevolutPaymentResponse());

        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class);

        //
        // WHEN
        //
        when(transactionRetrieval.pushPayments(anyString(), eq("https://sandbox-b2b.revolut.com/api/1.0/pay"), anyList(), eq(type))).thenReturn(responses);
        when(tokenService.getToken()).thenReturn("someToken");

        List<PaymentResponse> actual = transactionService.createPayments(payments);

        //
        // THEN
        //
        assertIterableEquals(responses, actual);
    }

    @Test
    public void getAccountsNormalConditions() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<Account> accounts = TestDataGenerator.generateRevolutAccounts();
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
        assertIterableEquals(accounts, actual);
    }

    @Test
    public void getTransactionsNormalConditions() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<Transaction> transactions = TestDataGenerator.generateRevolutTransactions();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutTransaction.class);

        when(transactionRetrieval.retrieveTransactions(
                ArgumentMatchers.anyString(),
                eq("https://sandbox-b2b.revolut.com/api/1.0/transactions"),
                eq(type))).thenReturn(transactions);
        when(tokenService.getToken()).thenReturn("someToken");

        //
        // WHEN
        //
        List<Transaction> actual = transactionService.retrieveTransactionData(null);

        //
        // THEN
        //
        assertIterableEquals(transactions, actual);
    }

    @Test
    public void mapIncompleteGeneralPayment() {
        //
        // GIVEN
        //
        GeneralPayment payment = TestDataGenerator.generateInvalidGeneralPayment();

        //
        // THEN
        //
        assertThrows(MissingPaymentRequestDataException.class, () -> mapper.toRevolutPayment(payment));
    }

    @Test
    public void mapCompleteGeneralPayment() {
        //
        // GIVEN
        //
        GeneralPayment payment = TestDataGenerator.generateInvalidGeneralPayment();
        Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("reference", "someReference");
        additionalInfo.put("counterparty", "testedValue");
        payment.setAdditionalInfo(additionalInfo);

        //
        // WHEN
        //
        RevolutPayment revolutPayment = mapper.toRevolutPayment(payment);

        //
        // THEN
        //
        assertEquals("testedValue", revolutPayment.getReceiver().getCounterpartyId());
    }
}
