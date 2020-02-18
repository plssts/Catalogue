package com.j2020.model;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonProcessingExceptionLambdaWrapper extends RuntimeException {
    public JsonProcessingExceptionLambdaWrapper(String message) {
        super(message);
    }
}
