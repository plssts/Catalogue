package com.j2020.service.deutsche;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.TokenFetchException;
import com.j2020.model.Transaction;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeutscheTransactionService implements TransactionService {
    private DeutscheTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;
    private DeutscheMultiFactorService multiFactor;

    @Value("${deutscheTransaction.ibanAvailableUrlPrepend}")
    private String ibanOnUrlPrepend;

    @Value("${deutscheTransaction.transactionUrl}")
    private String transactionUrl;

    public DeutscheTransactionService(DeutscheTokenService tokenRenewal, TransactionRequestRetrievalService transactionRetrieval, DeutscheMultiFactorService multiFactor) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
        this.multiFactor = multiFactor;
    }

    @Override
    public List<? extends Transaction> retrieveTransactionData(Optional<List<String>> ibans) {
        try {
            if (!ibans.isPresent()) {
                return new ArrayList<>();
            }

            String accessToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheTransaction.class);

            return ibans.get().stream()
                    .flatMap(current -> transactionRetrieval
                            .retrieveTransactions(accessToken, UriComponentsBuilder
                                    .fromUriString(transactionUrl)
                                    .queryParam("iban", current)
                                    .toUriString(), type)
                            .stream())
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException exception) {
            throw new TokenFetchException();
        }
    }

    @Override
    public List<? extends PaymentResponse> createPayments(List payments) {
        return null;
    }

    @Override
    public String demo() {
        System.out.println("Creating");

        String json1 = "{\n" +
                "  \"method\": \"PHOTOTAN\",\n" +
                "  \"requestType\": \"INSTANT_SEPA_CREDIT_TRANSFERS\",\n" +
                "  \"requestData\": {\n" +
                "    \"type\": \"challengeRequestDataInstantSepaCreditTransfers\",\n" +
                "    \"targetIban\": \"DE10010000000000005771\",\n" +
                "    \"amountCurrency\": \"EUR\",\n" +
                "    \"amountValue\": 1\n" +
                "    },\n" +
                "  \"language\": \"de\"\n" +
                "}";

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenRenewal.getToken());

        String url1 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single";
        HttpEntity<String> entity = new HttpEntity<String>(json1, headers);
        Map answer = restTemplate.postForObject(url1, entity, HashMap.class);

        System.out.println(answer);

        String id = answer.get("id").toString();

        String url2 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single/" + id;
        int field = multiFactor.getOneTimePass();
        String json2 = "{\n" +
                "  \"response\": \"" + field + "\"\n" +
                "}";

        System.out.println("Patching with " + field);

        HttpEntity<String> entity2 = new HttpEntity<String>(json2, headers);
        //ResponseEntity<HashMap> answer2 = restTemplate.exchange(url2, HttpMethod.PATCH, entity, HashMap.class);
        Map<String, String> answer2 = restTemplate.patchForObject(url2, entity2, HashMap.class);

        System.out.println(answer2);

        String json3 = "{\n" +
                "  \"debtorAccount\": {\n" +
                "    \"currencyCode\": \"EUR\",\n" +
                "    \"iban\": \"DE10010000000000005772\"\n" +
                "  },\n" +
                "  \"instructedAmount\": {\n" +
                "    \"amount\": 1,\n" +
                "    \"currencyCode\": \"EUR\"\n" +
                "  },\n" +
                "  \"creditorName\": \"Test Name\",\n" +
                "  \"creditorAccount\": {\n" +
                "    \"currencyCode\": \"EUR\",\n" +
                "    \"iban\": \"DE10010000000000005771\"\n" +
                "  }\n" +
                "}";
        headers.set("otp", answer2.get("otp"));
        //headers.set("Authorization", "otp "+ answer2.get("otp"));
        headers.set("idempotency-id", id/*generateRequestId()*/);

        System.out.println("Sending with headers\n" + headers);

        String url3 = "https://simulator-api.db.com:443/gw/dbapi/paymentInitiation/payments/v1/instantSepaCreditTransfers";
        HttpEntity<String> entity3 = new HttpEntity<String>(json3, headers);
        ResponseEntity<HashMap> answer3 = restTemplate.exchange(url3, HttpMethod.POST, entity3, HashMap.class);//postForObject(url3, entity3, HashMap.class);

        System.out.println(answer3);
        return "";
    }
}
