/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.j2020.model.TokenFetchException;

public interface TokenService {
    void refreshToken() throws TokenFetchException;
    String getToken();
}
