package com.alianza.demo.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class SharedKeyAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public SharedKeyAlreadyExistException() {
        super();
    }

    public SharedKeyAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SharedKeyAlreadyExistException(final String message) {
        super(message);
    }

    public SharedKeyAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
