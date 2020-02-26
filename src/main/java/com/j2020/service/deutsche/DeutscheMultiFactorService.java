/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.j2020.model.deutsche.DeutscheOneTimePassword;
import com.j2020.model.deutsche.DeutschePhototanChallengeResponse;
import com.j2020.model.deutsche.DeutschePhototanResponse;
import com.j2020.model.deutsche.DeutscheSepaPaymentRequest;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeutscheMultiFactorService {
    @Value("${deutscheTransaction.twoFactorSecret}")
    private String twoFactorSecret;

    @Value("${deutscheTransaction.oneTimePassUrl}")
    private String oneTimePassUrl;

    private GoogleAuthenticator authenticator;

    @Qualifier("mfaRestTemplate")
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(DeutscheMultiFactorService.class);

    public DeutscheMultiFactorService(GoogleAuthenticator authenticator, RestTemplate restTemplate) {
        this.authenticator = authenticator;
        this.restTemplate = restTemplate;
    }

    public String getOneTimePass() {
        StringBuilder otpValue = new StringBuilder(String.valueOf(authenticator.getTotpPassword(twoFactorSecret)));
        while (otpValue.length() < 6) {
            otpValue.insert(0, "0");
        }
        return otpValue.toString();
    }

    public Map<String, String> prepareAuthorisation(String token, String targetIban, String currency, String amount) {
        logger.info("Negotiating OTP for target account {}", targetIban);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        DeutscheSepaPaymentRequest sepaRequest = new DeutscheSepaPaymentRequest(targetIban, currency, amount);
        HttpEntity<DeutscheSepaPaymentRequest> entity = new HttpEntity<>(sepaRequest, headers);

        DeutschePhototanResponse sepaAnswer = restTemplate.postForObject(oneTimePassUrl, entity, DeutschePhototanResponse.class);

        String id = sepaAnswer.getId();
        String challengeUrl = UriComponentsBuilder.fromUriString(oneTimePassUrl).pathSegment(id).toUriString();

        DeutschePhototanChallengeResponse phototanResponse = new DeutschePhototanChallengeResponse(getOneTimePass());
        HttpEntity<DeutschePhototanChallengeResponse> challengeResponse = new HttpEntity<>(phototanResponse, headers);

        DeutscheOneTimePassword otpEntity = restTemplate.patchForObject(challengeUrl, challengeResponse, DeutscheOneTimePassword.class);

        HashMap<String, String> product = new HashMap<>();
        product.put("otp", otpEntity.getOneTimePassword());
        product.put("idempotency-id", id);

        return product;
    }
}
