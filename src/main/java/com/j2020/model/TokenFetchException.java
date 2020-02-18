/**
 * @author Paulius Staisiunas
 */

package com.j2020.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class TokenFetchException extends RuntimeException {
    public TokenFetchException() {
    }

    public TokenFetchException(String message) {
        super(message);
    }
}
