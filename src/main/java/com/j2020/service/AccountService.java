/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> retrieveAccountData() throws JsonProcessingException;
}
