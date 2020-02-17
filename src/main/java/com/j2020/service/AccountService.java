/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> retrieveAccountData(Optional<String> specificAccount);
}
