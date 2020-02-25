/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.j2020.model.*;
import com.j2020.model.deutsche.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeutscheMapperService {
    private static final Logger logger = LoggerFactory.getLogger(DeutscheMapperService.class);

    public GeneralTransaction toGeneralPayment(DeutscheTransaction transaction) {
        GeneralTransaction result = new GeneralTransaction();
        result.setTransactionId(transaction.getPaymentIdentification());
        result.setOrigin(transaction.getOriginIban());
        result.setCreditor(transaction.getCreditorId() != null ? transaction.getCreditorId() : transaction.getCounterPartyIban());
        result.setAmount(transaction.getAmount());
        result.setRequestId(transaction.getE2eReference());
        result.setReference(transaction.getPaymentReference());
        result.setCreatedAt(transaction.getBookingDate());
        result.setBank(Bank.DEUTSCHE);

        return result;
    }

    public GeneralAccount toGeneralAccount(DeutscheAccount account) {
        GeneralAccount result = new GeneralAccount();
        result.setAccountId(account.getAccountId());
        result.setBalance(account.getCurrentBalance());
        result.setCurrency(account.getCurrencyCode());
        result.setDescription(account.getProductDescription());
        result.setType(account.getAccountType());
        result.setBank(Bank.DEUTSCHE);

        return result;
    }

    public DeutschePayment toDeutschePayment(GeneralPayment payment) {
        logger.info("Attempting to construct DeutschePayment out of {}", payment);

        DeutschePayment result = new DeutschePayment();

        Float amount = Optional.ofNullable(payment.getAmount())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no amount specified"));
        String source = Optional.ofNullable(payment.getSourceAccount())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no source account specified"));
        String destination = Optional.ofNullable(payment.getDestinationAccount())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no destination account specified"));
        String currency = Optional.ofNullable(payment.getCurrency())
                .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no currency specified"));

        String creditorName;

        if (payment.getAdditionalInfo() != null) {
            creditorName = Optional.ofNullable(payment.getAdditionalInfo().get("creditorName"))
                    .orElseThrow(() -> new MissingPaymentRequestDataException(payment + " has no creditor name specified under 'additionalInfo'"));
        } else {
            throw new MissingPaymentRequestDataException(payment + " has no creditor name specified under 'additionalInfo'");
        }

        String adjustedAmountFormat = DeutscheSepaPaymentRequestData.formatValue(amount.toString());

        result.setCreditorName(creditorName);
        result.setDebtorAccount(new DeutscheSepaPaymentAccount(currency, source));
        result.setInstructedAmount(new DeutscheSepaPaymentAmount(adjustedAmountFormat, currency));
        result.setCreditorAccount(new DeutscheSepaPaymentAccount(currency, destination));

        return result;
    }
}
