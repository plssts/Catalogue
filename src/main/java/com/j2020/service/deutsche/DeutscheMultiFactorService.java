package com.j2020.service.deutsche;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DeutscheMultiFactorService {
    @Value("${deutscheTransaction.twoFactorSecret}")
    private String twoFactorSecret;

    private GoogleAuthenticator auth = new GoogleAuthenticator();

    public String getOneTimePass(){
        StringBuilder otpValue = new StringBuilder(String.valueOf(auth.getTotpPassword(twoFactorSecret)));
        while (otpValue.length() < 6){
            otpValue.insert(0, "0");
        }
        System.out.println("TOKEN: " + otpValue.toString()); // FIXME remove
        return otpValue.toString();
    }
}
