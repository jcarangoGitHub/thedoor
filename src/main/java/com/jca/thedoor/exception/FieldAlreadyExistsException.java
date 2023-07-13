package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FieldAlreadyExistsException extends ResponseStatusException {

    private static final String HEADER = "Status 409: Conflict. ";
    public FieldAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, HEADER + reason);
    }
}
