package net.chrisgrollier.cloud.apps.common.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;

/**
 * {@link RestExceptionHandler} dedicated to
 * {@link ConstraintViolationException}
 * 
 * @author Atos
 *
 */
public class ConstraintViolationExceptionHandler implements RestExceptionHandler<ConstraintViolationException> {

	/**
	 * i18n
	 */
	private final MessageManager messageManager;
	
	@Autowired
	public ConstraintViolationExceptionHandler(MessageManager messageManager) {
		super();
		this.messageManager = messageManager;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	/**
	 * Handle an exception of type {@link ConstraintViolationException}.
	 *
	 * @param ex
	 *            the ConstraintViolationException to be handled
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with a http status of 400 (BAD_REQUEST) where errors
	 *         field is populated with each violation constraints held by the
	 *         handled exception.
	 */
	public ResponseEntity<ExceptionResponse> handle(ConstraintViolationException ex) {
		final ExceptionResponse response = ExceptionResponse.of(HttpStatus.BAD_REQUEST);
		ex.getConstraintViolations().forEach(constraint -> response.addFieldError(constraint.getMessage()));
		return new ResponseEntity<>(response, BAD_REQUEST);
	}
}
