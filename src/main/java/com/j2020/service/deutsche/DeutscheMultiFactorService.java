package com.j2020.service.deutsche;

import com.j2020.controller.TransactionController;
import com.j2020.model.deutsche.DeutscheOneTimePassword;
import com.j2020.model.deutsche.DeutschePhototanChallengeResponse;
import com.j2020.model.deutsche.DeutschePhototanResponse;
import com.j2020.model.deutsche.DeutscheSepaPaymentRequest;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DeutscheMultiFactorService.class);

    public String getOneTimePass(){
        StringBuilder otpValue = new StringBuilder(String.valueOf(auth.getTotpPassword(twoFactorSecret)));
        while (otpValue.length() < 6){
            otpValue.insert(0, "0");
        }
        return otpValue.toString();
    }

    // FIXME move urls to application.properties
    public Map<String, String> prepareAuthorisation(String token, String targetIban, String currency, String amount){
        logger.info("Negotiating OTP for target account {}", targetIban);

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        String otpUrl = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single";

        DeutscheSepaPaymentRequest sepaRequest = new DeutscheSepaPaymentRequest(targetIban, currency, amount);
        HttpEntity<DeutscheSepaPaymentRequest> entity = new HttpEntity<>(sepaRequest, headers);

        DeutschePhototanResponse sepaAnswer = restTemplate.postForObject(otpUrl, entity, DeutschePhototanResponse.class);

        String id = sepaAnswer.getId();

        String challengeUrl = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single/" + id;

        DeutschePhototanChallengeResponse phototanResponse = new DeutschePhototanChallengeResponse(getOneTimePass());
        HttpEntity<DeutschePhototanChallengeResponse> challengeResponse = new HttpEntity<>(phototanResponse, headers);

        DeutscheOneTimePassword otpEntity = restTemplate.patchForObject(challengeUrl, challengeResponse, DeutscheOneTimePassword.class);

        HashMap<String, String> product = new HashMap<>();
        product.put("otp", otpEntity.getOneTimePassword());
        product.put("idempotency-id", id);

        return product;
    }
}
