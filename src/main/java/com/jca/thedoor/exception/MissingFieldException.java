package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MissingFieldException extends ResponseStatusException {

    private final static String MESSAGE_HEAD = "Faltan campos necesarios para ";

    public MissingFieldException(String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE_HEAD + reason);
    }
}
