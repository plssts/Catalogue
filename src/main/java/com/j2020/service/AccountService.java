/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Account;
import com.j2020.model.Bank;

import java.util.List;

public interface AccountService {
    List<Account> retrieveAccountData() throws JsonProcessingException;

    // FIXME add this to check if this bank is supported
    //boolean canProcess(Bank id);
}
