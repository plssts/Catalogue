/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;

import java.util.List;

public interface AccountService {
    List<GeneralAccount> retrieveAccountData() throws JsonProcessingException;

    boolean canProcessThisBank(Bank bankingService);
}
