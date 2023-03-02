package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FieldAlreadyExistsException extends ResponseStatusException {

    private static final String HEADER = "No es posible guardar el registro. Campo duplicado: ";
    public FieldAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, HEADER + reason);
    }
}
