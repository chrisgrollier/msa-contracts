/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the current user not allowed to perform an operation on a resource.
 */

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -4577084333812158700L;

    /**
     * Create a new UnauthorizedException for the given error message.
     * 
     * @param message a detail error message
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
