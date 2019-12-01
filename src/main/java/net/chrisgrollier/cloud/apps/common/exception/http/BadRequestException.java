/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when receiving an invalid cient request.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 6449948401504825350L;

    /**
     * Create a new BadRequestException for the given error message.
     * 
     * @param message a detail error message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
