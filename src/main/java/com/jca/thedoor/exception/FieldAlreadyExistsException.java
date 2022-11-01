package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FieldAlreadyExistsException extends ResponseStatusException {

    public FieldAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, reason);
        StringBuilder stringBuilder = new StringBuilder(reason);
    }
}
