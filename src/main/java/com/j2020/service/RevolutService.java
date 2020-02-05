package com.j2020.service;

//import jdk.nashorn.internal.parser.Token;
import com.j2020.service.AccountService;
import com.j2020.service.RevolutRenewalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutService implements AccountService {
    @Autowired
    private RevolutRenewalService tokenRenewal;

    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    @Override
    public String retrieveAccountData(){
        String url = "https://sandbox-b2b.revolut.com/api/1.0/accounts";

        // TODO only request a new token if the current one is broken
        String OAuthToken = tokenRenewal.getNewToken(OAuthJWT);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OAuthToken);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);
        } catch(HttpClientErrorException ex){
            return "An error has occurred.";
        }

        return response.getBody();
    }
}
