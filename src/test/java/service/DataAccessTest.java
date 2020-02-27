/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.J2020Application;
import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import com.j2020.model.GeneralPayment;
import com.j2020.model.GeneralTransaction;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.repository.AccountRepository;
import com.j2020.repository.TransactionRepository;
import com.j2020.service.AccountProcessingService;
import com.j2020.service.PersistenceManagerService;
import com.j2020.service.TransactionProcessingService;
import com.j2020.service.deutsche.DeutscheMapperService;
import com.j2020.service.revolut.RevolutMapperService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = J2020Application.class)

@Ignore
public class DataAccessTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private TransactionProcessingService transactionService;
    private AccountProcessingService accountService;
    private PersistenceManagerService persistence;
    private RevolutMapperService revolutMapper;
    private DeutscheMapperService deutscheMapper;

    @Before
    public void populateDatabase() {
        if (accountRepository.count() > 0 && transactionRepository.count() > 0) {
            return;
        }

        accountRepository.saveAll(TestDataHelper.generateRevolutAccounts().stream()
                .map(account -> revolutMapper.toGeneralAccount((RevolutAccount) account)).collect(Collectors.toList()));
        accountRepository.saveAll(TestDataHelper.generateDeutscheAccounts().stream()
                .map(account -> deutscheMapper.toGeneralAccount((DeutscheAccount) account)).collect(Collectors.toList()));

        transactionRepository.saveAll(TestDataHelper.generateRevolutTransactions().stream()
                .map(transaction -> revolutMapper.toGeneralTransaction((RevolutTransaction) transaction)).collect(Collectors.toList()));
        transactionRepository.saveAll(TestDataHelper.generateDeutscheTransactions().stream()
                .map(transaction -> deutscheMapper.toGeneralTransaction((DeutscheTransaction) transaction)).collect(Collectors.toList()));
    }

    @Before
    public void setUp() {
        transactionService = Mockito.mock(TransactionProcessingService.class);
        accountService = Mockito.mock(AccountProcessingService.class);
        revolutMapper = new RevolutMapperService();
        deutscheMapper = new DeutscheMapperService();
        persistence = new PersistenceManagerService(transactionService, accountService, accountRepository, transactionRepository);
    }

    @Test
    public void getAllAccounts() {
        // GIVEN
        Map<String, List<GeneralAccount>> accounts = persistence.returnAccounts();

        // THEN
        assertEquals(2, accounts.get(Bank.REVOLUT.toString()).size());
        assertEquals(1, accounts.get(Bank.DEUTSCHE.toString()).size());
    }

    @Test
    public void getAllTransactions() {
        // GIVEN
        Map<String, List<GeneralTransaction>> transactions = persistence.returnTransactions();

        // THEN
        assertEquals(1, transactions.get(Bank.REVOLUT.toString()).size());
        assertEquals(2, transactions.get(Bank.DEUTSCHE.toString()).size());
    }

    @Test
    public void processAndUpdateTransactions() throws JsonProcessingException {
        // GIVEN
        Map<String, List<GeneralTransaction>> addedTransactions = new HashMap<>();
        addedTransactions.put(Bank.REVOLUT.toString(), TestDataHelper.generateGeneralTransactionsUnder(Bank.REVOLUT));
        addedTransactions.put(Bank.DEUTSCHE.toString(), TestDataHelper.generateGeneralTransactionsUnder(Bank.DEUTSCHE));

        Map<String, List<GeneralPayment>> payments = new HashMap<>();
        List<GeneralPayment> generalPayments = new ArrayList<>();
        generalPayments.add(new GeneralPayment());
        payments.put(Bank.REVOLUT.toString(), generalPayments);
        payments.put(Bank.DEUTSCHE.toString(), generalPayments);

        Map<String, List<GeneralAccount>> accounts = new HashMap<>();
        accounts.put(Bank.REVOLUT.toString(), TestDataHelper.generateRevolutAccounts().stream()
                .map(account -> revolutMapper.toGeneralAccount((RevolutAccount) account)).collect(Collectors.toList()));
        accounts.put(Bank.DEUTSCHE.toString(), TestDataHelper.generateDeutscheAccounts().stream()
                .map(account -> deutscheMapper.toGeneralAccount((DeutscheAccount) account)).collect(Collectors.toList()));

        //when(transactionService.initiatePaymentRequests(ArgumentMatchers.anyMap())).thenReturn(new HashMap<>());
        when(transactionService.collectTransactionResponse()).thenReturn(addedTransactions);
        when(accountService.collectAccountResponse()).thenReturn(accounts);

        // WHEN
        persistence.processAndUpdateTransactions(payments);

        // THEN
        assertEquals(3, transactionRepository.findByBank(Bank.REVOLUT).size());
        assertEquals(4, transactionRepository.findByBank(Bank.DEUTSCHE).size());
    }
}
