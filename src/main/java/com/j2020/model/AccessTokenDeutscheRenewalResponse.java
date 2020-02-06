package com.j2020.model;

import lombok.Data;

@Data
public class AccessTokenDeutscheRenewalResponse implements TokenRenewalResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String id_token;

    public String getAccessToken(){
        return access_token;
    }
}
