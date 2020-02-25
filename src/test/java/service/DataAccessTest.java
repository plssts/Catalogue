/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.J2020Application;
//import com.j2020.repository.DeutscheAccountRepository;
import com.j2020.service.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = J2020Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataAccessTest {
    //@Autowired
    //private DeutscheAccountRepository deutscheAccountRepository;
    //@Autowired
    //private RevolutAccountRepository revolutAccountRepository;
    //@Autowired
    //private DeutscheTransactionRepository deutscheTransactionRepository;
    //@Autowired
    //private RevolutTransactionRepository revolutTransactionRepository;

    private TransactionProcessingService transactionService;
    //private RepositoryFactory repositoryFactory;
    private AccountProcessingService accountService;
    private PersistenceManagerService persistence;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // populate in memory H2 here with test data
    }

    @Before
    public void setUp() throws JsonProcessingException {
        transactionService = Mockito.mock(TransactionProcessingService.class);
        accountService = Mockito.mock(AccountProcessingService.class);

        /*Map<String, JpaRepository<Account, String>> accounts = new HashMap<>();
        accounts.put(Bank.REVOLUT.toString(), revolutAccountRepository);
        accounts.put(Bank.DEUTSCHE.toString(), deutscheAccountRepository);

        Map<String, JpaRepository<Transaction, String>> transactions = new HashMap<>();
        transactions.put(Bank.REVOLUT.toString(), revolutTransactionRepository);
        transactions.put(Bank.DEUTSCHE.toString(), deutscheTransactionRepository);*/

        /*repositoryFactory = new RepositoryFactory(accounts, transactions);
        persistence = new PersistenceManagerService(transactionService, repositoryFactory, accountService);*/
    }

    @Test
    public void getAllAccounts() {
        //
        // GIVEN
        //
        /*Map<String, List<Account>> accounts = persistence.returnAccounts();

        //
        // THEN
        //
        assertEquals(6, accounts.get(Bank.REVOLUT.toString()).size());
        assertEquals(2, accounts.get(Bank.DEUTSCHE.toString()).size());*/
    }

    @Test
    public void getAllTransactions() {
        //
        // GIVEN
        //
        /*Map<String, List<Transaction>> transactions = persistence.returnTransactions();

        //
        // THEN
        //
        assertEquals(72, transactions.get(Bank.REVOLUT.toString()).size());
        assertEquals(8, transactions.get(Bank.DEUTSCHE.toString()).size());*/
    }

    @Test
    public void processAndUpdateTransactions() throws JsonProcessingException {
        //
        // GIVEN
        //
        /*Map<String, List<Transaction>> addedTransactions = new HashMap<>();
        addedTransactions.put("REVOLUT", TestDataHelper.generateRevolutTransactions());
        addedTransactions.put("DEUTSCHE", TestDataHelper.generateDeutscheTransactions());

        List<String> revolutTransactions = new ArrayList<>();
        revolutTransactions.add("6a7ce85-00c3c11-daf");

        List<String> deutscheTransactions = new ArrayList<>();
        deutscheTransactions.add("DB-A001/01");
        deutscheTransactions.add("DB-A952/41");

        Map<String, List<GeneralPayment>> payments = new HashMap<>();
        List<GeneralPayment> generalPayments = new ArrayList<>();
        generalPayments.add(new GeneralPayment());
        payments.put("REVOLUT", generalPayments);
        payments.put("DEUTSCHE", generalPayments);

        Map<String, List<Account>> accounts = new HashMap<>();
        accounts.put("REVOLUT", TestDataHelper.generateRevolutAccounts());
        accounts.put("DEUTSCHE", TestDataHelper.generateDeutscheAccounts());

        when(transactionService.initiatePaymentRequests(ArgumentMatchers.anyMap())).thenReturn(new HashMap<>());
        when(transactionService.collectTransactionResponse()).thenReturn(addedTransactions);
        when(accountService.collectAccountResponse()).thenReturn(accounts);

        //
        // WHEN
        //
        persistence.processAndUpdateTransactions(payments);

        //
        // THEN
        //
        assertEquals(revolutTransactionRepository.findAllById(revolutTransactions).size(), 1);
        assertEquals(deutscheTransactionRepository.findAllById(deutscheTransactions).size(), 2);*/
    }
}
