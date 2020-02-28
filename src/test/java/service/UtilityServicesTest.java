/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Bank;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.service.AccountService;
import com.j2020.service.BankingServiceFactory;
import com.j2020.service.GlobalExceptionHandler;
import com.j2020.service.TransactionService;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheTransactionService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class UtilityServicesTest {
    private BankingServiceFactory bankingServiceFactory;
    private GlobalExceptionHandler exceptionHandler;
    private RevolutAccountService revolutAccountService;
    private DeutscheAccountService deutscheAccountService;
    private RevolutTransactionService revolutTransactionService;
    private DeutscheTransactionService deutscheTransactionService;

    @Before
    public void setUp() {
        revolutAccountService = Mockito.mock(RevolutAccountService.class);
        deutscheAccountService = Mockito.mock(DeutscheAccountService.class);
        revolutTransactionService = Mockito.mock(RevolutTransactionService.class);
        deutscheTransactionService = Mockito.mock(DeutscheTransactionService.class);

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
