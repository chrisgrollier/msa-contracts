/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that the requested service is not yet implemented.
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = 8963764238968989830L;

    /**
     * Create a new NotImplementedException for the given error message.
     * 
     * @param message a detail error message
     */
    public NotImplementedException(String message) {
        super(message);
    }
}
