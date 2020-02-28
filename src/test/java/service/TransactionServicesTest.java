/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.*;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutschePaymentResponse;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.model.exception.BankNotSupportedException;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.BankingServiceFactory;
import com.j2020.service.TransactionProcessingService;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheMapperService;
import com.j2020.service.deutsche.DeutscheMultiFactorService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.jms.JmsTransactionProducer;
import com.j2020.service.revolut.RevolutMapperService;
import com.j2020.service.revolut.RevolutTransactionService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class TransactionServicesTest {
    private TransactionProcessingService processingService;
    private BankingServiceFactory serviceFactory;
    private RevolutTransactionService revolutTransactionService;
    private DeutscheTransactionService deutscheTransactionService;
    private DeutscheAccountService deutscheAccountService;
    private DeutscheMultiFactorService multiFactorService;
    private TransactionRequestRetrievalService retrievalService;
    private RestTemplate restTemplate;
    private JmsTransactionProducer transactionProducer;

    @Before
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        multiFactorService = Mockito.mock(DeutscheMultiFactorService.class);
        serviceFactory = Mockito.mock(BankingServiceFactory.class);
        revolutTransactionService = Mockito.mock(RevolutTransactionService.class);
        deutscheTransactionService = Mockito.mock(DeutscheTransactionService.class);
        deutscheAccountService = Mockito.mock(DeutscheAccountService.class);
        transactionProducer = Mockito.mock(JmsTransactionProducer.class);
        processingService = new TransactionProcessingService(serviceFactory, transactionProducer);
        retrievalService = new TransactionRequestRetrievalService(multiFactorService, restTemplate);

        setField(retrievalService, "maxReqIdLength", 40);
    }

    @Test
    public void gettingTransactionsNormalConditions() throws JsonProcessingException {
        // GIVEN
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutTransaction.class);
        Transaction body = TestDataHelper.generateRevolutTransactions().get(0);
        List<Transaction> expected = new ArrayList<>();
        expected.add(body);
        ResponseEntity<String> response = new ResponseEntity<>(new ObjectMapper().writeValueAsString(body), HttpStatus.OK);

        // WEHN
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), notNull(), eq(String.class))).thenReturn(response);
        List<Transaction> actual = retrievalService.retrieveTransactions(Constants.TEST_ACCESS_TOKEN, Constants.REVOLUT_TRANSACTION_URL, type);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void initiatingWithInvalidParams() {
        // THEN
        assertThrows(BankNotSupportedException.class, () -> processingService.initiatePaymentRequests(new HashMap<>()));
    }

    @Test
    public void messagePassesNormalConditions() throws JsonProcessingException {
        // GIVEN
        Map<String, List<GeneralPayment>> params = new HashMap<>();
        BatchOfPaymentsMessage response = new BatchOfPaymentsMessage();
        response.setBatchId(1L);

        List<PaymentResponse> revolutResponse = new ArrayList<>();
        revolutResponse.add(TestDataHelper.generateRevolutPaymentResponse());

        List<GeneralPayment> payment = new ArrayList<>();
        payment.add(TestDataHelper.generateValidGeneralPaymentForRevolut());

        params.put(Bank.REVOLUT.toString(), payment);

        // WHEN
        when(transactionProducer.sendPayments(anyList())).thenReturn(response);

        BatchOfPaymentsMessage actual = processingService.initiatePaymentRequests(params);

        // THEN
        assertEquals(response.getBatchId(), actual.getBatchId());
    }

    @Test
    public void throwsWithFailedMapping() throws JsonProcessingException {
        // WHEN
        when(deutscheAccountService.retrieveAccountData()).thenThrow(JsonProcessingException.class);
        when(serviceFactory.retrieveTransactionService(eq(Bank.REVOLUT))).thenReturn(revolutTransactionService);
        when(revolutTransactionService.retrieveTransactionData(null)).thenReturn(null);
        when(serviceFactory.retrieveAccountService(eq(Bank.DEUTSCHE))).thenReturn(deutscheAccountService);

        // THEN
        assertThrows(JsonProcessingExceptionLambdaWrapper.class, () -> processingService.collectTransactionResponse());
    }

    @Test
    public void transactionsFetchedNormalConditions() throws JsonProcessingException {
        // GIVEN
        List<GeneralTransaction> revoTransactions = TestDataHelper.generateRevolutTransactions().stream()
                .map(account -> new RevolutMapperService().toGeneralTransaction((RevolutTransaction) account)).collect(Collectors.toList());
        List<GeneralTransaction> deutscheTransactions = TestDataHelper.generateDeutscheTransactions().stream()
                .map(account -> new DeutscheMapperService().toGeneralTransaction((DeutscheTransaction) account)).collect(Collectors.toList());
        List<GeneralAccount> deutscheAccounts = TestDataHelper.generateDeutscheAccounts().stream()
                .map(account -> new DeutscheMapperService().toGeneralAccount((DeutscheAccount) account)).collect(Collectors.toList());

        Map<String, List<GeneralTransaction>> expected = new HashMap<>();
        expected.put(Bank.REVOLUT.toString(), revoTransactions);
        expected.put(Bank.DEUTSCHE.toString(), deutscheTransactions);

        // WHEN
        when(serviceFactory.retrieveTransactionService(eq(Bank.REVOLUT))).thenReturn(revolutTransactionService);
        when(serviceFactory.retrieveTransactionService(eq(Bank.DEUTSCHE))).thenReturn(deutscheTransactionService);
        when(serviceFactory.retrieveAccountService(eq(Bank.DEUTSCHE))).thenReturn(deutscheAccountService);
        when(revolutTransactionService.retrieveTransactionData(null)).thenReturn(revoTransactions);
        when(deutscheTransactionService.retrieveTransactionData(anyList())).thenReturn(deutscheTransactions);
        when(deutscheAccountService.retrieveAccountData()).thenReturn(deutscheAccounts);

        Map<String, List<GeneralTransaction>> actual = processingService.collectTransactionResponse();

        // THEN
        assertEquals(expected, actual);
    }
}
