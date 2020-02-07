/**
 * @author Paulius Staisiunas
 */

package com.j2020.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class DeutscheTokenRenewalResponse implements TokenRenewalResponse {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int
    private String scope;
    private String id_token;


}
