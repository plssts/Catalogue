/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutschePaymentResponse;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheMapperService;
import com.j2020.service.deutsche.DeutscheTokenService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import helper.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

// FIXME move strings to @Value
public class DeutscheServicesTest {
    private DeutscheTokenService tokenService;
    private AccountRequestRetrievalService accountRetrieval;
    private TransactionRequestRetrievalService transactionRetrieval;
    private DeutscheAccountService accountService;
    private DeutscheTransactionService transactionService;
    private DeutscheMapperService mapper;

    @Before
    public void setUp() {
        mapper = new DeutscheMapperService();
        tokenService = Mockito.mock(DeutscheTokenService.class);
        accountRetrieval = Mockito.mock(AccountRequestRetrievalService.class);
        accountService = new DeutscheAccountService(tokenService, accountRetrieval);
        transactionRetrieval = Mockito.mock(TransactionRequestRetrievalService.class);
        transactionService = new DeutscheTransactionService(tokenService, transactionRetrieval, mapper);

        setField(accountService, "accountUrl", "https://simulator-api.db.com/gw/dbapi/v1/cashAccounts");
        setField(transactionService, "transactionUrl", "https://simulator-api.db.com/gw/dbapi/v1/transactions");
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
        payments.add(TestDataGenerator.generateValidGeneralPaymentForDeutsche());

        List<PaymentResponse> responses = new ArrayList<>();
        responses.add(TestDataGenerator.generateDeutschePaymentResponse());

        JavaType type = new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class);

        //
        // WHEN
        //
        when(transactionRetrieval.pushPayments(anyString(), eq("https://simulator-api.db.com/gw/dbapi/paymentInitiation/payments/v1/instantSepaCreditTransfers"), anyList(), eq(type))).thenReturn(responses);
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
        List<Account> accounts = TestDataGenerator.generateDeutscheAccounts();
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
        assertIterableEquals(accounts, actual);
    }

    @Test
    public void getTransactionsNormalConditions() throws JsonProcessingException {
        //
        // GIVEN
        //
        List<Transaction> transactions = TestDataGenerator.generateDeutscheTransactions();
        List<String> dummyIban = new ArrayList<>();
        dummyIban.add("DE00100500");
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheTransaction.class);

        when(transactionRetrieval.retrieveTransactions(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                eq(type))).thenReturn(transactions);
        when(tokenService.getToken()).thenReturn("someToken");

        //
        // WHEN
        //
        List<Transaction> actual = transactionService.retrieveTransactionData(dummyIban);

        //
        // THEN
        //
        assertIterableEquals(transactions, actual);
    }

    @Test
    public void stripTrailingNullDecimals() {
        //
        // GIVEN
        //
        GeneralPayment general = TestDataGenerator.generateValidGeneralPaymentForDeutsche();
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
    public void retainActualDecimalPart() {
        //
        // GIVEN
        //
        GeneralPayment general = TestDataGenerator.generateValidGeneralPaymentForDeutsche();
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

    @Test
    public void mapIncompleteGeneralPayment() {
        //
        // GIVEN
        //
        GeneralPayment payment = TestDataGenerator.generateInvalidGeneralPayment();

        //
        // THEN
        //
        assertThrows(MissingPaymentRequestDataException.class, () -> mapper.toDeutschePayment(payment));
    }
}
