/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.TokenFetchException;

public interface AccountService {
    String retrieveAccountData();
    String retrieveSpecificAccount(String account) throws TokenFetchException;
}
