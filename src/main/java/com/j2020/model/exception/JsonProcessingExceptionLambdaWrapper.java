/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.exception;

public class JsonProcessingExceptionLambdaWrapper extends RuntimeException {
    public JsonProcessingExceptionLambdaWrapper(String message) {
        super(message);
    }
}
