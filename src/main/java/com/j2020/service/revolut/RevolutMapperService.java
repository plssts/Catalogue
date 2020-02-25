/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.revolut;

import com.j2020.model.*;
import com.j2020.model.revolut.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevolutMapperService {
    private static final Logger logger = LoggerFactory.getLogger(RevolutMapperService.class);

    public GeneralTransaction toGeneralTransaction(RevolutTransaction transaction) {
        GeneralTransaction result = new GeneralTransaction();
        result.setTransactionId(transaction.getId());
        result.setOrigin(transaction.getRevolutLegs().get(0).getAccountId());
        result.setCreditor(transaction.getRevolutLegs().get(0).getCounterparty() != null ? transaction.getRevolutLegs().get(0).getCounterparty().getAccountId() : null);
        result.setAmount(transaction.getRevolutLegs().get(0).getAmount());
        result.setType(transaction.getType());
        result.setState(transaction.getState());
        result.setRequestId(transaction.getRequestId());
        result.setReference(transaction.getReference());
        result.setCreatedAt(transaction.getDateOfCreating());
        result.setBank(Bank.REVOLUT);

        return result;
    }

    public GeneralAccount toGeneralAccount(RevolutAccount account) {
        GeneralAccount result = new GeneralAccount();
        result.setAccountId(account.getAccountId());
        result.setBalance(account.getBalance());
        result.setCurrency(account.getCurrency());
        result.setDescription(account.getName());
        result.setType(account.getState());
        result.setBank(Bank.REVOLUT);

        return result;
    }

    public RevolutPayment toRevolutPayment(GeneralPayment payment) {
        logger.info("Attempting to construct RevolutPayment out of {}", payment);

        RevolutPayment result = new RevolutPayment();

        Float amount = Optional.ofNullable(payment.getAmount())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no amount specified"));
        String source = Optional.ofNullable(payment.getSourceAccount())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no source account specified"));
        String destination = Optional.ofNullable(payment.getDestinationAccount())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no destination account specified"));
        String currency = Optional.ofNullable(payment.getCurrency())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no currency specified"));

        String reference;
        String counterparty;

        if (payment.getAdditionalInfo() != null) {
            reference = Optional.ofNullable(payment.getAdditionalInfo().get("reference"))
                    .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no reference specified under 'additionalInfo'"));
            counterparty = Optional.ofNullable(payment.getAdditionalInfo().get("counterparty"))
                    .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no counterparty specified under 'additionalInfo'"));
        } else {
            throw new MissingPaymentRequestDataException(payment + " has no reference and counterparty specified under 'additionalInfo'");
        }

        RevolutTransactionLegCounterparty receiver = new RevolutTransactionLegCounterparty();
        receiver.setAccountId(destination);
        receiver.setCounterpartyId(counterparty);

        result.setAccountId(source);
        result.setReference(reference);
        result.setAmount(amount);
        result.setCurrency(currency);
        result.setReceiver(receiver);

        return result;
    }
}
