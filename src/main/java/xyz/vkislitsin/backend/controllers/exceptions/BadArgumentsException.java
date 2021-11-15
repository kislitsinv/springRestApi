package xyz.vkislitsin.backend.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadArgumentsException extends RuntimeException {
    public BadArgumentsException(String message) {
        super(message);
    }
}