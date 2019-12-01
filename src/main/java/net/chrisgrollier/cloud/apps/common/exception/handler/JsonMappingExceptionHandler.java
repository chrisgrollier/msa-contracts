package net.chrisgrollier.cloud.apps.common.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.text.MessageFormat;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

/**
 * {@link RestExceptionHandler} dedicated to {@link JsonMappingException}
 * 
 * @author Atos
 *
 */
public class JsonMappingExceptionHandler implements RestExceptionHandler<JsonMappingException> {

	public static final String UNKNOWN_PROPERTY_MSG = "Unknown property";
	public static final String INVALID_VALUE_MSG = "Invalid value. Expected value of type {0} but got: {1}";
	public static final String MISSING_REQUIRED_VALUE_MSG = "The parameter is missing";
	public static final String JSON_PARSE_ERROR_MSG = "Cannot parse JSON data";

	/**
	 * Handle an exception of type {@link JsonMappingException}.
	 *
	 * @param ex
	 *            the JsonMappingException to be handled
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with a http status of 400 (BAD_REQUEST).
	 */
	public ResponseEntity<ExceptionResponse> handle(JsonMappingException ex) {
		final ExceptionResponse response = ExceptionResponse.of(BAD_REQUEST);
		// @formatter:off
        final String path = ex.getPath()
                              .stream()
                              .map(Reference::getFieldName)
                              .collect(Collectors.joining("."));
        // @formatter:on
		if (ex instanceof InvalidFormatException) {
			final InvalidFormatException ife = (InvalidFormatException) ex;

			response.addFieldError(FieldError.of(path,
					formatMessage(INVALID_VALUE_MSG, ife.getTargetType().getSimpleName(), ife.getValue())));
			return new ResponseEntity<>(response, BAD_REQUEST);
		}
		if (ex instanceof IgnoredPropertyException) {
			response.addFieldError(FieldError.of(path, MISSING_REQUIRED_VALUE_MSG));
			return new ResponseEntity<>(response, BAD_REQUEST);
		}
		if (ex instanceof UnrecognizedPropertyException) {
			response.addFieldError(FieldError.of(path, UNKNOWN_PROPERTY_MSG));
			return new ResponseEntity<>(response, BAD_REQUEST);
		}

		response.addFieldError(FieldError.of(path, JSON_PARSE_ERROR_MSG));
		return new ResponseEntity<>(response, BAD_REQUEST);

	}

	private String formatMessage(String format, Object... params) {
		return MessageFormat.format(format, params);
	}

}
