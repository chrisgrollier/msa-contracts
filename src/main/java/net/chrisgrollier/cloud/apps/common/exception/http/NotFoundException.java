/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that an access to a resource requested by a client does not exist.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5375905907418627763L;

    /**
     * Create a new NotFoundException for the given error message.
     * 
     * @param message a detail error message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
