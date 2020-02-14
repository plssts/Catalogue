package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.TokenFetchException;
import com.j2020.model.Transaction;
import com.j2020.model.deutsche.*;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutPaymentResponse;
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
    public List<? extends PaymentResponse> createPayments(List<? extends Payment> payments) {
        return null;
    }

    @Override
    public String demo() { // FIXME remove this properly
        String json1 = "{\n" +
                "  \"method\": \"PHOTOTAN\",\n" +
                "  \"requestType\": \"INSTANT_SEPA_CREDIT_TRANSFERS\",\n" +
                "  \"requestData\": {\n" +
                "    \"type\": \"challengeRequestDataInstantSepaCreditTransfers\",\n" +
                "    \"targetIban\": \"DE10010000000000005771\",\n" +
                "    \"amountCurrency\": \"EUR\",\n" +
                "    \"amountValue\": \"1\"\n" +
                "    },\n" +
                "  \"language\": \"de\"\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();

        //DeutscheSepaPaymentRequest request = new DeutscheSepaPaymentRequest("DE10010000000000005771", "EUR", "1.0");

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenRenewal.getToken());

        String url1 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single";
        //ResponseEntity<String> response = restTemplate.exchange(url1, HttpMethod.POST, new HttpEntity<>(json1, headers), String.class);

        DeutscheSepaPaymentRequest object = new DeutscheSepaPaymentRequest("DE10010000000000005771", "EUR", "1.0");
        //System.out.println(object.getRequestData().getAmountValue());

        HttpEntity<DeutscheSepaPaymentRequest> entity = new HttpEntity<>(object, headers);

        //HttpEntity<String> entity = new HttpEntity<>(json1, headers);
        DeutschePhototanResponse answer = restTemplate.postForObject(url1, entity, DeutschePhototanResponse.class);


        /*DeutschePhototanResponse answer = null;
        try {
            answer = mapper.readValue(response.getBody(), mapper.getTypeFactory().constructType(DeutschePhototanResponse.class));
        } catch (JsonProcessingException e) {
            System.out.println("jackson failed");
            e.printStackTrace();
        }*/
        //DeutschePhototanResponse answer = restTemplate.postForObject(url1, entity, DeutschePhototanResponse.class);

        String id = answer.getId();

        String url2 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single/" + id;
        String field = multiFactor.getOneTimePass();
        String json2 = "{\n" +
                "  \"response\": " + field + "\n" +
                "}";

        System.out.println("Patching with " + field);
        System.out.println("json2: " + json2);

        HttpEntity<String> entity2 = new HttpEntity<>(json2, headers);
        //ResponseEntity<HashMap> answer2 = restTemplate.exchange(url2, HttpMethod.PATCH, entity, HashMap.class);
        Map<String, String> answer2 = restTemplate.patchForObject(url2, entity2, HashMap.class);

        System.out.println(answer2);

        /* OTP response
        {
        otp=eyJhbGciOiJSUzI1NiJ9.eyJ0eCI6IntcInR5cGVcIjpcIlBJU19JTlNUQU5UX1BBWU1FTlRfR1JBTlRfUEVSTUlTU0lPTlwiLFwiYWNjb3VudElkXCI6XCJERTEwMDEwMDAwMDAwMDAwMDA1NzcxXCIsXCJpbnN0cnVjdGVkQW1vdW50XCI6e1wiY3VycmVuY3lcIjpcIkVVUlwiLFwidmFsdWVcIjoxfSxcIm1ldGFkYXRhXCI6e1wiYXV0aG9yaXphdGlvblN0YXR1c1wiOlwiRklOQUxJWkVEXCIsXCJwYXltZW50VHlwZVwiOlwiSU5TVEFOVF9TRVBBX0NSRURJVF9UUkFOU0ZFUlNcIixcImlkXCI6bnVsbH19IiwiaXNzIjoiZ2x1ZXNpbSIsImV4cCI6MTU4MTY3NzQ2NX0.PALHNcMKEPqMNZLvi2wQNmonjlnVZ2QZqaXar0AO0bddM5WESJXdK4IXp1y0YyZKMqvSXz7qBSEfAAMM0Ybf3R4rUMEFYKQViua9U67XIBmxiJQiT7MjkXKLrBV5C447I0gnngJ2AIpT7cpTWuqcy1YgRoZ8nxoUODQQU5StGxaJ3A56y6JjJci9EhPFSUXWXymqPsp472pMeYbT202AOLFjx6QGTa2DEggD_fqexv-eCDnIFkZ_Nlxw5JEMFRrUJ7OL1R7F_vSZiee7y2qVHaaCi_mBq2XMLXpEkZbvfrbmNQErUuC2s2gVp-70AkMR7f5U1lihAVzsf1AD6rfAAw
        }

         */

        String json3 = "{\n" +
                "  \"debtorAccount\": {\n" +
                "    \"currencyCode\": \"EUR\",\n" +
                "    \"iban\": \"DE10010000000000005772\"\n" +
                "  },\n" +
                "  \"instructedAmount\": {\n" +
                "    \"amount\": \"1\",\n" +
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
        HttpEntity<String> entity3 = new HttpEntity<>(json3, headers);
        //ResponseEntity<HashMap> answer3 = restTemplate.exchange(url3, HttpMethod.POST, entity3, HashMap.class);
        HashMap answer3 = restTemplate.postForObject(url3, entity3, HashMap.class);

        System.out.println(answer3);

        /* Payment response
        {transactionStatus=PDNG, paymentId=RTEff313ab5-8aea-40f9-a5a7-ffe62113f662}
         */
        return "";
    }
}
