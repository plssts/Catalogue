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
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> extra = multiFactor.prepareAuthorisation(tokenRenewal.getToken());
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        String currency = "EUR";
        String creditorName = "test";
        String currentIban = "DE10010000000000005772";
        String amount = DeutscheSepaPaymentRequestData.formatValue("1.0");

        DeutschePayment payment = new DeutschePayment(
                new DeutscheSepaPaymentAccount(currency, currentIban),
                new DeutscheSepaPaymentAmount(amount, currency),
                creditorName,
                new DeutscheSepaPaymentAccount(currency, "DE10010000000000005771"));

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
        headers.set("otp", extra.get("otp"));
        headers.set("idempotency-id", extra.get("idempotency-id"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", "Bearer " + tokenRenewal.getToken());

        //System.out.println("Sending with headers\n" + headers);

        String url3 = "https://simulator-api.db.com/gw/dbapi/paymentInitiation/payments/v1/instantSepaCreditTransfers";

        List<DeutschePayment> pay = new ArrayList<>();
        pay.add(payment);

        return transactionRetrieval.pushPayments(tokenRenewal.getToken(), Optional.of(extra), url3, pay, new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class));

    }

    @Override
    public String demo() { // FIXME remove this properly
        /*RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenRenewal.getToken());

        String url1 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single";
        //ResponseEntity<String> response = restTemplate.exchange(url1, HttpMethod.POST, new HttpEntity<>(json1, headers), String.class);

        DeutscheSepaPaymentRequest object = new DeutscheSepaPaymentRequest("DE10010000000000005771", "EUR", "1.0");

        HttpEntity<DeutscheSepaPaymentRequest> entity = new HttpEntity<>(object, headers);

        DeutschePhototanResponse answer = restTemplate.postForObject(url1, entity, DeutschePhototanResponse.class);

        String id = answer.getId();

        String url2 = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single/" + id;
        String field = multiFactor.getOneTimePass();

        DeutschePhototanChallengeResponse phototanResponse = new DeutschePhototanChallengeResponse(multiFactor.getOneTimePass());

        String json2 = "{\n" +
                "  \"response\": " + field + "\n" +
                "}";

        HttpEntity<DeutschePhototanChallengeResponse> entity2 = new HttpEntity<>(phototanResponse, headers);
        //ResponseEntity<HashMap> answer2 = restTemplate.exchange(url2, HttpMethod.PATCH, entity, HashMap.class);

        DeutscheOneTimePassword answer2 = restTemplate.patchForObject(url2, entity2, DeutscheOneTimePassword.class);*/

        /*HttpHeaders headers = new HttpHeaders();
        Map<String, String> extra = multiFactor.prepareAuthorisation(tokenRenewal.getToken());
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        String currency = "EUR";
        String creditorName = "test";
        String currentIban = "DE10010000000000005772";
        String amount = DeutscheSepaPaymentRequestData.formatValue("1.0");

        DeutschePayment payment = new DeutschePayment(
                new DeutscheSepaPaymentAccount(currency, currentIban),
                new DeutscheSepaPaymentAmount(amount, currency),
                creditorName,
                new DeutscheSepaPaymentAccount(currency, "DE10010000000000005771"));

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
        headers.set("otp", extra.get("otp"));
        headers.set("idempotency-id", extra.get("idempotency-id"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", "Bearer " + tokenRenewal.getToken());

        //System.out.println("Sending with headers\n" + headers);

        String url3 = "https://simulator-api.db.com/gw/dbapi/paymentInitiation/payments/v1/instantSepaCreditTransfers";

        List<DeutschePayment> pay = new ArrayList<>();
        pay.add(payment);

        return transactionRetrieval.pushPayments(tokenRenewal.getToken(), Optional.of(extra), url3, pay, new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutschePaymentResponse.class));

        /*
        HttpEntity<String> entity3 = new HttpEntity<>(json3, headers);
        //ResponseEntity<HashMap> answer3 = restTemplate.exchange(url3, HttpMethod.POST, entity3, HashMap.class);
        HashMap answer3 = restTemplate.postForObject(url3, entity3, HashMap.class);

        System.out.println(answer3);*/

        /* Payment response
        {transactionStatus=PDNG, paymentId=RTEff313ab5-8aea-40f9-a5a7-ffe62113f662}
         */
        return "";
    }
}
