/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.Constants;
import com.j2020.model.*;
import com.j2020.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryFactory {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryFactory.class);

    //@Autowired
    //Map<String, JpaRepository<Account, String>> accountRepositories;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;
    //List<RevolutAccountRepository> accountRepositories;// = new ArrayList<>();

    //Map<String, JpaRepository<Transaction, String>> transactionRepositories;
    //@Autowired
    //List<JpaRepository<Transaction, String>> transactionRepositories = new ArrayList<>();

    //private final DeutscheAccountRepository deutscheAccountRepository;
    //private final DeutscheTransactionRepository deutscheTransactionRepository;
    //private final RevolutAccountRepository revolutAccountRepository;
    //private final RevolutTransactionRepository revolutTransactionRepository;

    public RepositoryFactory(/*DeutscheAccountRepository deutscheAccountRepository,*/
                             /*DeutscheTransactionRepository deutscheTransactionRepository,*/
                             /*RevolutAccountRepository revolutAccountRepository,*/
                             /*RevolutTransactionRepository revolutTransactionRepository*/) {
        //this.deutscheAccountRepository = deutscheAccountRepository;
        //this.deutscheTransactionRepository = deutscheTransactionRepository;
        //this.revolutAccountRepository = revolutAccountRepository;
        //this.revolutTransactionRepository = revolutTransactionRepository;

        //accountRepositories.add(deutscheAccountRepository);
        //accountRepositories.add(revolutAccountRepository);
    }

    public JpaRepository/*<Transaction, String>*/ retrieveTransactionRepository(Bank bankingService) {
        logger.info("Fetching {} transaction repository", bankingService);

        return transactionRepository;
        /*return transactionRepositories.stream()
                .filter(acc -> acc.getClass().getAnnotation(Repository.class).value().equals(bankingService))
                .findAny()
                .orElseThrow(() -> new BankNotSupportedException("Should never happen."));*/

        //return transactionRepositories.get(bankingService.toString());
        //stream().filter(transactionStringJpaRepository -> transactionStringJpaRepository.getClass().isInstance(JpaRepository));

        /*switch (bankingService) {
            case REVOLUT:
                return revolutTransactionRepository;

            case DEUTSCHE:
                return deutscheTransactionRepository;
        }
        throw new BankNotSupportedException("Should never happen.");*/
    }

    public JpaRepository<GeneralAccount, String> retrieveAccountRepository(Bank bankingService) {
        logger.info("Fetching general account repository");

        //return accountRepositories.get(bankingService.toString());
        /*System.out.println(" SHOULD BE FALSE" + accountRepository.isEmpty());
        System.out.println("Searching for " + bankingService.toString());
        System.out.println(accountRepository.get(0).getClass());
        System.out.println(accountRepository.get(1).getClass());*/
        //AnnotatedElementUtils.getAllAnnotationAttributes(accountRepository.get(0).getClass(), );
        //System.out.println(AnnotationUtils.findAnnotation(accountRepository.get(0).getClass(), Repository.class).value());
        return accountRepository;

        /*switch (bankingService) {
            case REVOLUT:
                return revolutAccountRepository;

            case DEUTSCHE:
                return deutscheAccountRepository;
        }
        throw new BankNotSupportedException("Should never happen.");*/
    }
}
