package net.chrisgrollier.cloud.apps.common.exception.handler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * {@link RestExceptionHandler} dedicated to spring exception of type
 * {@link TypeMismatchException}.
 * 
 * @author Atos
 *
 */
public class TypeMismatchExceptionHandler implements RestExceptionHandler<TypeMismatchException> {

	/**
	 * Handle spring exceptions of type {@link TypeMismatchException}.
	 * 
	 * @param ex
	 *            the exception to be handled
	 * 
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with a http status of 400 (BAD_REQUEST) where errors
	 *         field is populated with property and mismatch error message held by
	 *         handled exception
	 */
	public ResponseEntity<ExceptionResponse> handle(TypeMismatchException ex) {
		ExceptionResponse response = ExceptionResponse.of(HttpStatus.BAD_REQUEST);
		response.addFieldError(getFieldError(ex));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	private String getPropertyName(TypeMismatchException mismatchException) {
		if (mismatchException instanceof MethodArgumentTypeMismatchException)
			return ((MethodArgumentTypeMismatchException) mismatchException).getName();

		return mismatchException.getPropertyName();
	}

	private FieldError getFieldError(final TypeMismatchException mismatchException) {
		final String property = getPropertyName(mismatchException);
		return FieldError.of(property, mismatchException.getMessage());
	}

}
