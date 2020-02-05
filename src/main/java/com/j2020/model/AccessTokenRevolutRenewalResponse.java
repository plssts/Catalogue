package com.j2020.model;

import lombok.Data;

@Data
public class AccessTokenRevolutRenewalResponse {
    private String access_token;
    private String token_type;
    private int expires_in;

    public String getAccessToken(){
        return access_token;
    }
}
