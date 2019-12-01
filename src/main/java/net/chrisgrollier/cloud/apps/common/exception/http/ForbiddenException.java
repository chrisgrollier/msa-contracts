/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that an access to a resource requested by a client has been forbidden by the server.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1364509743731517786L;

    /**
     * Create a new ForbiddenException for the given error message.
     * 
     * @param message a detail error message
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
