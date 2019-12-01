package net.chrisgrollier.cloud.apps.common.exception.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import net.chrisgrollier.cloud.apps.common.exception.handler.support.BasicWebAppExceptionHandler;

/**
 * This class represent the error object that is converted to json. Any
 * exception that occurs in the application should be transformed to an instance
 * of that class. This job is normally done by our handlers, in particular the
 * {@link BasicWebAppExceptionHandler} that aggregate our set of handlers.
 * 
 * @author Atos
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

	/** The error code. */
	private final HttpStatus statusCode;

	/** The error message description. */
	private String message;

	/** The List of field errors. */
	private final Collection<FieldError> errors = Lists.newArrayList();

	private ExceptionResponse(HttpStatus statusCode, String message) {
		this.statusCode = checkNotNull(statusCode, "Status code is required");
		this.message = message;
	}

	/**
	 * Instantiates a new {@link ExceptionResponse}
	 *
	 * @param code
	 *            the code
	 * @return the error dto
	 */
	public static ExceptionResponse of(final HttpStatus statusCode) {
		return new ExceptionResponse(statusCode, statusCode.getReasonPhrase());
	}

	/**
	 * Instantiates a new {@link ExceptionResponse}
	 *
	 * @param code
	 *            the error code as {@link HttpStatus}
	 * @param message
	 *            the error message description.
	 * @return the new errorDto instance.
	 */
	public static ExceptionResponse of(final HttpStatus statusCode, final String message) {
		return new ExceptionResponse(statusCode, message);
	}

	/**
	 * Adds a new field error.
	 *
	 * @param message
	 *            the error message description.
	 */
	public void addFieldError(final String message) {
		errors.add(FieldError.of(message));
	}

	/**
	 * Adds the new field error.
	 *
	 * @param field
	 *            the field name.
	 * @param message
	 *            the error message description
	 */
	public void addFieldError(String field, final String message) {
		errors.add(FieldError.of(field, message));
	}

	/**
	 * Adds the field error to a field error list.
	 *
	 * @param fieldError
	 *            the field error to be added.
	 */
	public void addFieldError(final FieldError fieldError) {
		errors.add(fieldError);
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<FieldError> getErrors() {
		return Collections.unmodifiableCollection(errors);
	}

	@Override
	public String toString() {
		// @formatter:off
        return MoreObjects.toStringHelper(this)
                          .add("statusCode", statusCode)
                          .add("message", message)
                          .add("errors", errors)
                          .add("errors", errors)
                          .toString();
        // @formatter:on

	}

}
