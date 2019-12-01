package net.chrisgrollier.cloud.apps.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * {@link RestExceptionHandler} dedicated to various spring web mvc exception
 * such as {@link HttpMediaTypeNotAcceptableException},
 * {@link HttpMediaTypeNotSupportedException},
 * {@link HttpRequestMethodNotSupportedException},
 * {@link MissingServletRequestParameterException},
 * {@link MissingServletRequestPartException} and
 * {@link NoHandlerFoundException}.
 * 
 * @author Atos
 *
 */
public class ServletExceptionHandler implements RestExceptionHandler<Exception> {

	/**
	 * Handle many spring web mvc exception an exception
	 *
	 * @param ex
	 *            the exception to be handled
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with the http status depending on the handled exception
	 *         class. {@link MissingServletRequestParameterException},
	 *         {@link MissingServletRequestPartException} and
	 *         {@link NoHandlerFoundException} will set the http status to 400
	 *         BAD_REQUEST. The {@link HttpMediaTypeNotAcceptableException} will set
	 *         the http status to 406 NOT_ACCEPTABLE. The
	 *         {@link HttpMediaTypeNotSupportedException} will set the http status
	 *         to 415 UNSUPPORTED_MEDIA_TYPE. The
	 *         {@link HttpRequestMethodNotSupportedException} will set the http
	 *         status to 405 METHOD_NOT_ALLOWED. Any other handled exception will
	 *         set the http status to 500 INTERNAL_SERVER_ERROR.
	 */
	public ResponseEntity<ExceptionResponse> handle(Exception exception) {

		if (exception instanceof MissingServletRequestParameterException) {
			final MissingServletRequestParameterException ex = (MissingServletRequestParameterException) exception;
			final ExceptionResponse response = ExceptionResponse.of(HttpStatus.BAD_REQUEST);
			response.addFieldError(FieldError.of(ex.getParameterName(), ex.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (exception instanceof MissingServletRequestPartException || exception instanceof NoHandlerFoundException) {
			final ExceptionResponse response = ExceptionResponse.of(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (exception instanceof HttpMediaTypeNotAcceptableException) {
			final ExceptionResponse response = ExceptionResponse.of(HttpStatus.NOT_ACCEPTABLE);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (exception instanceof HttpMediaTypeNotSupportedException) {
			final ExceptionResponse response = ExceptionResponse.of(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
			return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}

		if (exception instanceof HttpRequestMethodNotSupportedException) {
			final HttpRequestMethodNotSupportedException ex = (HttpRequestMethodNotSupportedException) exception;
			final StringBuilder errorMsg = new StringBuilder();
			errorMsg.append(ex.getLocalizedMessage());
			errorMsg.append(". Supported methods are: ");
			// @formatter:off
            ex.getSupportedHttpMethods()
              .forEach(methode -> errorMsg.append(methode + " "));
            // @formatter:on
			final ExceptionResponse response = ExceptionResponse.of(HttpStatus.METHOD_NOT_ALLOWED, errorMsg.toString());
			return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);

		}

		return new ResponseEntity<>(ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
