package com.j2020.service.revolut;

import com.j2020.model.GeneralPayment;
import com.j2020.model.MissingPaymentRequestDataException;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutscheSepaPaymentAccount;
import com.j2020.model.deutsche.DeutscheSepaPaymentAmount;
import com.j2020.model.deutsche.DeutscheSepaPaymentRequestData;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutTransactionLegCounterparty;
import com.j2020.service.Mapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevolutMapperService implements Mapper {
    public RevolutPayment toRevolutPayment(GeneralPayment payment) {
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

        if (payment.getAdditionalInfo() != null){
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
