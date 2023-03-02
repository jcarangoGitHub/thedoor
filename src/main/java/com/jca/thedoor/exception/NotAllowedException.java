package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAllowedException extends ResponseStatusException {

    public NotAllowedException(String reason) {
        super(HttpStatus.METHOD_NOT_ALLOWED, reason);
    }
}
