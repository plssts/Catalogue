package com.j2020.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MissingPaymentRequestDataException extends RuntimeException {
    public MissingPaymentRequestDataException(String message) {
        super(message);
    }
}
