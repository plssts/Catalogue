/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.JsonProcessingExceptionLambdaWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({JsonProcessingException.class, HttpClientErrorException.class, JsonProcessingExceptionLambdaWrapper.class})
    public ResponseEntity<String> handleExceptions(Exception exception) {
        logger.error("Exception raised during operation");

        if (exception instanceof JsonProcessingException ||
                exception instanceof JsonProcessingExceptionLambdaWrapper) {

            HttpStatus status = HttpStatus.BAD_GATEWAY;
            return new ResponseEntity<>("Unexpected response object returned. " + exception.getMessage(), status);

        } else if (exception instanceof HttpClientErrorException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>("Communication error. " + exception.getMessage(), status);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(exception.getMessage(), status);
    }
}
