/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the server received an invalid response from another server.
 */
@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class BadGatewayException extends RuntimeException {

    private static final long serialVersionUID = -832516280415164180L;

    /**
     * Create a new BadGatewayException for the given error message.
     * 
     * @param message a detail error message
     */
    public BadGatewayException(String message) {
        super(message);
    }
}
