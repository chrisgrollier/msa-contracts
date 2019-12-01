/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown in case of encountering an unsupported media type.
 */
@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaType extends RuntimeException {

    private static final long serialVersionUID = 3326497555403974269L;

    /**
     * Create a new UnsupportedMediaType for the given error message.
     * @param message a detail error message
     */
    public UnsupportedMediaType(String message) {
        super(message);
    }
}
