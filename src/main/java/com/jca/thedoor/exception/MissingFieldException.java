package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MissingFieldException extends ResponseStatusException {

    public MissingFieldException(String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }
}
