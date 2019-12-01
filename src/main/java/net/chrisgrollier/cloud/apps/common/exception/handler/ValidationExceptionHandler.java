package net.chrisgrollier.cloud.apps.common.exception.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * {@link RestExceptionHandler} dedicated to spring validation exceptions such
 * as {@link MethodArgumentNotValidException} and exception that implements
 * {@link BindingResult}.
 * 
 * @author Atos
 *
 */
public class ValidationExceptionHandler implements RestExceptionHandler<Exception> {

	/**
	 * Handle spring validation exceptions such as
	 * {@link MethodArgumentNotValidException} and exception that implements
	 * {@link BindingResult}
	 *
	 * @param ex
	 *            the exception to be handled
	 * 
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with a http status of 400 (BAD_REQUEST). If the exception
	 *         is {@link MethodArgumentNotValidException} or implements
	 *         {@link BindingResult}, the {@link BindingResult#getFieldError()} is
	 *         used to populate the errors field
	 */
	public ResponseEntity<ExceptionResponse> handle(Exception ex) {
		final ExceptionResponse response = ExceptionResponse.of(HttpStatus.BAD_REQUEST);
		BindingResult br = getBindingResult(ex);
		if (br != null) {
			List<FieldError> errors = br.getFieldErrors();
			errors.forEach(fieldError -> response.addFieldError(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	private BindingResult getBindingResult(final Exception exception) {
		return exception instanceof BindingResult ? ((BindingResult) exception)
				: ((MethodArgumentNotValidException) exception).getBindingResult();

	}

}
