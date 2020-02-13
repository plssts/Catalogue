package com.j2020.service.deutsche;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DeutscheMultiFactorService {
    @Value("${deutscheTransaction.twoFactorSecret}")
    private String twoFactorSecret;
    private GoogleAuthenticator auth = new GoogleAuthenticator();

    public int getOneTimePass(){
        return auth.getTotpPassword(twoFactorSecret);
    }
}
