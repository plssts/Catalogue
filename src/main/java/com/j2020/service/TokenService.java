/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TokenService {
    void refreshToken() throws JsonProcessingException;

    String getToken();
}
