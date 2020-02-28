/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.service.AccountProcessingService;
import com.j2020.service.AccountRequestRetrievalService;
import com.j2020.service.BankingServiceFactory;
import com.j2020.service.deutsche.DeutscheAccountService;
import com.j2020.service.deutsche.DeutscheMapperService;
import com.j2020.service.revolut.RevolutAccountService;
import com.j2020.service.revolut.RevolutMapperService;
import helper.TestDataHelper;
import org.junit.Before;
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

public class AccountServicesTest {
    private AccountProcessingService processingService;
    private BankingServiceFactory serviceFactory;
    private RevolutAccountService revolutAccountService;
    private DeutscheAccountService deutscheAccountService;
    private AccountRequestRetrievalService retrievalService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        serviceFactory = Mockito.mock(BankingServiceFactory.class);
        revolutAccountService = Mockito.mock(RevolutAccountService.class);
        deutscheAccountService = Mockito.mock(DeutscheAccountService.class);
        processingService = new AccountProcessingService(serviceFactory);
        retrievalService = new AccountRequestRetrievalService(restTemplate);
    }

    @Test
    public void retrievesAccountsInArrayNormalConditions() throws JsonProcessingException {
        // GIVEN
        RevolutAccount account = new RevolutAccount();
        account.setAccountId(Constants.REVOLUT_CLIENT_ID);
        account.setName("test");
        account.setCurrency("EUR");
        account.setBalance(10f);
        ResponseEntity<String> response = new ResponseEntity<>(new ObjectMapper().writeValueAsString(account), HttpStatus.OK);

        List<Account> expected = new ArrayList<>();
        expected.add(account);

        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutAccount.class);

        // WHEN
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), notNull(), eq(String.class))).thenReturn(response);
        List<Account> actual = retrievalService.retrieveAccounts(Constants.TEST_ACCESS_TOKEN, Constants.REVOLUT_ACCOUNT_URL, type);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void throwsIfMappingErrorsOccurred() throws JsonProcessingException {
        // WHEN
        when(revolutAccountService.retrieveAccountData()).thenThrow(JsonProcessingException.class);
        when(serviceFactory.retrieveAccountService(eq(Bank.REVOLUT))).thenReturn(revolutAccountService);

        // THEN
        assertThrows(JsonProcessingExceptionLambdaWrapper.class, () -> processingService.collectAccountResponse());
    }

    @Test
    public void accountsFetchedNormalConditions() throws JsonProcessingException {
        // GIVEN
        List<GeneralAccount> revoAccounts = TestDataHelper.generateRevolutAccounts().stream()
                .map(account -> new RevolutMapperService().toGeneralAccount((RevolutAccount) account)).collect(Collectors.toList());
        List<GeneralAccount> deutscheAccounts = TestDataHelper.generateDeutscheAccounts().stream()
                .map(account -> new DeutscheMapperService().toGeneralAccount((DeutscheAccount) account)).collect(Collectors.toList());

        Map<String, List<GeneralAccount>> expected = new HashMap<>();
        expected.put(Bank.REVOLUT.toString(), revoAccounts);
        expected.put(Bank.DEUTSCHE.toString(), deutscheAccounts);

        // WHEN
        when(serviceFactory.retrieveAccountService(eq(Bank.REVOLUT))).thenReturn(revolutAccountService);
        when(serviceFactory.retrieveAccountService(eq(Bank.DEUTSCHE))).thenReturn(deutscheAccountService);
        when(revolutAccountService.retrieveAccountData()).thenReturn(revoAccounts);
        when(deutscheAccountService.retrieveAccountData()).thenReturn(deutscheAccounts);

        Map<String, List<GeneralAccount>> actual = processingService.collectAccountResponse();

        // THEN
        assertEquals(expected, actual);
    }
}
