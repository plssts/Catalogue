package com.j2020.service.deutsche;

import com.j2020.model.deutsche.DeutscheOneTimePassword;
import com.j2020.model.deutsche.DeutschePhototanChallengeResponse;
import com.j2020.model.deutsche.DeutschePhototanResponse;
import com.j2020.model.deutsche.DeutscheSepaPaymentRequest;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeutscheMultiFactorService {
    @Value("${deutscheTransaction.twoFactorSecret}")
    private String twoFactorSecret;

    private final GoogleAuthenticator auth = new GoogleAuthenticator();

    public String getOneTimePass(){
        StringBuilder otpValue = new StringBuilder(String.valueOf(auth.getTotpPassword(twoFactorSecret)));
        while (otpValue.length() < 6){
            otpValue.insert(0, "0");
        }
        return otpValue.toString();
    }

    public Map<String, String> prepareAuthorisation(String token){
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        String url1 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single";

        DeutscheSepaPaymentRequest object = new DeutscheSepaPaymentRequest("DE10010000000000005771", "EUR", "1.0");

        HttpEntity<DeutscheSepaPaymentRequest> entity = new HttpEntity<>(object, headers);

        DeutschePhototanResponse answer = restTemplate.postForObject(url1, entity, DeutschePhototanResponse.class);

        String id = answer.getId();
        String url2 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single/" + id;

        DeutschePhototanChallengeResponse phototanResponse = new DeutschePhototanChallengeResponse(getOneTimePass());

        HttpEntity<DeutschePhototanChallengeResponse> entity2 = new HttpEntity<>(phototanResponse, headers);

        DeutscheOneTimePassword answer2 = restTemplate.patchForObject(url2, entity2, DeutscheOneTimePassword.class);

        HashMap<String, String> product = new HashMap<>();
        product.put("otp", answer2.getOneTimePassword());
        product.put("idempotency-id", id);

        return product;
    }
}
