/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

public interface TokenService {
    void refreshToken();
    String getToken();
}
