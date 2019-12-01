/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception represents the errors encountered in the internal server.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = -7025477454629554479L;

    /**
     * Create a new InternalServerException for the given error message.
     * 
     * @param message a detail error message
     */
    public InternalServerException(String message) {
        super(message);
    }
}
