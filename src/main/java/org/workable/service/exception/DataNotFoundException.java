package org.workable.service.exception;

/**
 * Movie not found exception returned by the application
 * in case movie does not exists
 */
public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
