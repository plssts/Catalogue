/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.Constants;
import com.j2020.model.Bank;
import com.j2020.model.deutsche.DeutscheOneTimePassword;
import com.j2020.model.deutsche.DeutschePhototanResponse;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.service.AccountService;
import com.j2020.service.BankingServiceFactory;
import com.j2020.service.GlobalExceptionHandler;
import com.j2020.service.TransactionService;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheMultiFactorService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTransactionService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GoogleAuthenticator.class)
public class UtilityServicesTest {
    private BankingServiceFactory bankingServiceFactory;
    private GlobalExceptionHandler exceptionHandler;
    private RevolutAccountService revolutAccountService;
    private DeutscheAccountService deutscheAccountService;
    private RevolutTransactionService revolutTransactionService;
    private DeutscheTransactionService deutscheTransactionService;
    private DeutscheMultiFactorService multiFactorService;
    private GoogleAuthenticator googleAuthenticator;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        revolutAccountService = Mockito.mock(RevolutAccountService.class);
        deutscheAccountService = Mockito.mock(DeutscheAccountService.class);
        revolutTransactionService = Mockito.mock(RevolutTransactionService.class);
        deutscheTransactionService = Mockito.mock(DeutscheTransactionService.class);
        restTemplate = Mockito.mock(RestTemplate.class);
        googleAuthenticator = Mockito.mock(GoogleAuthenticator.class);
        multiFactorService = new DeutscheMultiFactorService(googleAuthenticator, restTemplate);

        setField(multiFactorService, "twoFactorSecret", Constants.DEUTSCHE_TWO_FACTOR_SECRET);
        setField(multiFactorService, "oneTimePassUrl", Constants.DEUTSCHE_ONE_TIME_PASS_URL);

        List<AccountService> accounts = new ArrayList<>();
        accounts.add(revolutAccountService);
        accounts.add(deutscheAccountService);

        List<TransactionService> transactions = new ArrayList<>();
        transactions.add(revolutTransactionService);
        transactions.add(deutscheTransactionService);

        bankingServiceFactory = new BankingServiceFactory(accounts, transactions);
        exceptionHandler = new GlobalExceptionHandler();
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
    public void onHttpException() {
        // GIVEN
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        // WHEN
        HttpStatus actual = exceptionHandler.handleExceptions(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void onJsonProcessingException() {
        // GIVEN
        HttpStatus expected = HttpStatus.BAD_GATEWAY;

        // WHEN
        HttpStatus actual = exceptionHandler.handleExceptions(new JsonProcessingException("") {
        }).getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void onJsonLambdaException() {
        // GIVEN
        HttpStatus expected = HttpStatus.BAD_GATEWAY;

        // WHEN
        HttpStatus actual = exceptionHandler.handleExceptions(new JsonProcessingExceptionLambdaWrapper("")).getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void factoryGetsAccountService() {
        // GIVEN
        AccountService expected = revolutAccountService;

        // WHEN
        when(revolutAccountService.canProcessThisBank(eq(Bank.REVOLUT))).thenReturn(true);
        AccountService actual = bankingServiceFactory.retrieveAccountService(Bank.REVOLUT);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void factoryGetsTransactionService() {
        // GIVEN
        TransactionService expected = deutscheTransactionService;

        // WHEN
        when(deutscheTransactionService.canProcessThisBank(eq(Bank.DEUTSCHE))).thenReturn(true);
        TransactionService actual = bankingServiceFactory.retrieveTransactionService(Bank.DEUTSCHE);

        // THEN
        assertEquals(expected, actual);
    }
}
