package com.j2020.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class MFA {
    private final String secret = "PRE6QIOR5XL77XOV";
    private GoogleAuthenticator auth = new GoogleAuthenticator();

    public int getOTP(){
        return auth.getTotpPassword(secret);
    }
}
