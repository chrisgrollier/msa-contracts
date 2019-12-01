/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {

    private static final long serialVersionUID = -9005217578879148228L;

    /**
     * Create a new ServiceUnavailableException for the given error message.
     * 
     * @param message a detail error message
     */
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
