/**
 * @author Paulius Staisiunas
 */

package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import com.j2020.model.GeneralPayment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.Transaction;
import com.j2020.model.deutsche.DeutscheAccount;
import com.j2020.model.deutsche.DeutschePaymentResponse;
import com.j2020.model.deutsche.DeutscheTokenRenewalResponse;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.model.revolut.*;

import java.time.LocalDateTime;
import java.util.*;

public class TestDataHelper {
    public static DeutscheTokenRenewalResponse generateExpiredDeutscheTokenResponse() {
        DeutscheTokenRenewalResponse renewalResponse = new DeutscheTokenRenewalResponse();
        renewalResponse.setAccessToken("expiredAnyway");
        renewalResponse.setSecondsUntilExpiring(-1);
        renewalResponse.setTokenType("access_token");
        renewalResponse.setAllowedOperations("[access_accounts]");
        renewalResponse.setIdentityToken("609-a9285-c11");
        renewalResponse.setRefreshToken("validRefreshToken");

        return renewalResponse;
    }

    public static RevolutTokenRenewalResponse generateExpiredRevolutTokenResponse() {
        RevolutTokenRenewalResponse renewalResponse = new RevolutTokenRenewalResponse();
        renewalResponse.setAccessToken("expiredAnyway");
        renewalResponse.setSecondsUntilExpiring(-1);
        renewalResponse.setTokenType("access_token");

        return renewalResponse;
    }

    public static PaymentResponse generateDeutschePaymentResponse() {
        DeutschePaymentResponse response = new DeutschePaymentResponse();
        response.setPaymentId("PaymentIdentification");
        response.setTransactionStatus("PNDG");

        return response;
    }

    public static PaymentResponse generateRevolutPaymentResponse() {
        RevolutPaymentResponse response = new RevolutPaymentResponse();
        response.setId("PaymentIdentification");
        response.setCreatedAt(LocalDateTime.now().toString());
        response.setState("pending");

        return response;
    }

    public static GeneralPayment generateValidGeneralPaymentForDeutsche() {
        GeneralPayment payment = new GeneralPayment();
        payment.setAmount(10f);
        payment.setSourceAccount("anyValidAccount");
        payment.setDestinationAccount("anyValidAccount");
        payment.setCurrency("EUR");

        Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("creditorName", "Receiver");
        payment.setAdditionalInfo(additionalInfo);

        return payment;
    }

    public static GeneralPayment generateValidGeneralPaymentForRevolut() {
        GeneralPayment payment = new GeneralPayment();
        payment.setAmount(10f);
        payment.setSourceAccount("anyValidAccount");
        payment.setDestinationAccount("anyValidAccount");
        payment.setCurrency("EUR");

        Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("reference", "Notes for this transfer");

        RevolutTransactionLegCounterparty counterparty = new RevolutTransactionLegCounterparty();
        counterparty.setId(1L);
        counterparty.setAccountId("REVAccountID");
        counterparty.setAccountType("private");

        try {
            additionalInfo.put("counterparty", new ObjectMapper().writeValueAsString(counterparty));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // TODO log a generator failure here
        }
        payment.setAdditionalInfo(additionalInfo);

        return payment;
    }

    public static List<Transaction> generateRevolutTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        RevolutTransactionLeg revolutLeg = new RevolutTransactionLeg();
        RevolutTransactionLegCounterparty revolutLegCounterparty = new RevolutTransactionLegCounterparty();

        RevolutTransaction demoTransactionOne = new RevolutTransaction();
        demoTransactionOne.setId("6a7ce85-00c3c11-daf");
        demoTransactionOne.setDateOfCreating("1984-04-04");

        revolutLeg.setLegId("9954e25a451f4c870d");
        revolutLeg.setAccountId("REVOACCOUNTID");
        revolutLeg.setAmount(500f);
        revolutLeg.setCurrency("EUR");

        revolutLegCounterparty.setCounterpartyId("oftenOptional");
        revolutLegCounterparty.setAccountId("REVORECEIVERID");

        revolutLeg.setCounterparty(revolutLegCounterparty);
        demoTransactionOne.setRevolutLegs(Collections.singletonList(revolutLeg));

        transactions.add(demoTransactionOne);

        return transactions;
    }

    public static List<Account> generateRevolutAccounts() {
        List<Account> accounts = new ArrayList<>();

        RevolutAccount demoResponseAccountOne = new RevolutAccount();
        demoResponseAccountOne.setAccountId("800");
        demoResponseAccountOne.setName("savings");
        demoResponseAccountOne.setBalance(500.1f);
        demoResponseAccountOne.setCurrency("EUR");
        demoResponseAccountOne.setState("active");
        demoResponseAccountOne.setPublic(false);
        demoResponseAccountOne.setDateOfCreating(LocalDateTime.now().toString());
        demoResponseAccountOne.setDateOfUpdating(LocalDateTime.now().toString());

        RevolutAccount demoResponseAccountTwo = new RevolutAccount();
        demoResponseAccountTwo.setAccountId("801");
        demoResponseAccountTwo.setName("business");
        demoResponseAccountTwo.setBalance(3700f);
        demoResponseAccountTwo.setCurrency("EUR");
        demoResponseAccountTwo.setState("active");
        demoResponseAccountTwo.setPublic(true);
        demoResponseAccountTwo.setDateOfCreating(LocalDateTime.now().toString());
        demoResponseAccountTwo.setDateOfUpdating(LocalDateTime.now().toString());

        accounts.add(demoResponseAccountOne);
        accounts.add(demoResponseAccountTwo);

        return accounts;
    }

    public static GeneralPayment generateInvalidGeneralPayment() {
        GeneralPayment payment = new GeneralPayment();
        payment.setAmount(100f);
        payment.setSourceAccount("anyValidAccount");
        payment.setDestinationAccount("anyValidAccount");
        payment.setCurrency("EUR");

        return payment;
    }

    public static List<Account> generateDeutscheAccounts() {
        List<Account> accounts = new ArrayList<>();

        DeutscheAccount demoResponseAccount = new DeutscheAccount();
        demoResponseAccount.setAccountId("DE0001");
        demoResponseAccount.setBic("BIC = code");
        demoResponseAccount.setCurrencyCode("EUR");
        demoResponseAccount.setCurrentBalance(80000f);
        demoResponseAccount.setAccountType("public");

        accounts.add(demoResponseAccount);

        return accounts;
    }

    public static List<Transaction> generateDeutscheTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        DeutscheTransaction demoTransactionOne = new DeutscheTransaction();
        demoTransactionOne.setOriginIban("DE00100500");
        demoTransactionOne.setAmount(905.28f);
        demoTransactionOne.setCounterPartyIban("DE00200400");
        demoTransactionOne.setCurrencyCode("EUR");
        demoTransactionOne.setPaymentIdentification("DB-A001/01");

        DeutscheTransaction demoTransactionTwo = new DeutscheTransaction();
        demoTransactionTwo.setOriginIban("DE00100500");
        demoTransactionTwo.setAmount(0.99f);
        demoTransactionTwo.setCounterPartyIban("DE00400500");
        demoTransactionTwo.setCurrencyCode("EUR");
        demoTransactionTwo.setPaymentIdentification("DB-A952/41");

        transactions.add(demoTransactionOne);
        transactions.add(demoTransactionTwo);

        return transactions;
    }
}
