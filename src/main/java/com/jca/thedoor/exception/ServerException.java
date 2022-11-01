package com.jca.thedoor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServerException extends ResponseStatusException {
    public ServerException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }
}
