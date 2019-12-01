package net.chrisgrollier.cloud.apps.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * {@link RestExceptionHandler} dedicated to
 * {@link ResponseStatusException}
 * 
 * @author Atos
 *
 */
public class ResponseStatusExceptionHandler implements RestExceptionHandler<ResponseStatusException> {

	/**
	 * Handle an exception of type {@link ResponseStatusException}.
	 *
	 * @param ex
	 *            the ResponseStatusException to be handled
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with the http status held by the handled exception
	 */
    public ResponseEntity<ExceptionResponse> handle(ResponseStatusException exception) {
        final HttpStatus status = exception.getStatus();
        final ExceptionResponse response = ExceptionResponse.of(status, exception.getMessage());
        return new ResponseEntity<>(response, status);
    }
}
