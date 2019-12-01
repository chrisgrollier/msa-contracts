package net.chrisgrollier.cloud.apps.common.exception.handler;

import java.text.MessageFormat;

import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.chrisgrollier.cloud.apps.common.exception.FrameworkFunctionalException;
import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;

/**
 * {@link RestExceptionHandler} dedicated to a particular http status and any
 * type of exception.
 * 
 * @author Atos
 *
 */
public class ServiceExceptionHandler implements RestExceptionHandler<Exception> {

	private final HttpStatus status;

	private final MessageManager messageManager;

	/**
	 * Create a new instance dedicated to the provided http status
	 * 
	 * @param status
	 *            the status
	 */
	public ServiceExceptionHandler(HttpStatus status, MessageManager messageManager) {
		super();
		this.status = status;
		this.messageManager = messageManager;
	}

	/**
	 * Handle an exception
	 *
	 * @param ex
	 *            the exception to be handled
	 * @return a {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with the http status field {@link #status} and the
	 *         exception message {@link Exception#getMessage()}.
	 */
	public ResponseEntity<ExceptionResponse> handle(Exception exception) {
		return new ResponseEntity<>(this.i18nResponse(exception), status);
	}

	/**
	 * Return a message related to the given exception.
	 * 
	 * @param throwable
	 *            base exception
	 * @return exception message if the given exception is NOT a
	 *         {@link FrameworkFunctionalException} otherwise if we have
	 *         {@link MessageManager}, we try to retrieve the message from it. If it
	 *         fails and we have at least one item in the messageArgs array of the
	 *         exception, we use a {@link MessageFormat} to produce the effective
	 *         message
	 */
	protected ExceptionResponse i18nResponse(Throwable throwable) {
		// init message with exception.getMessage()
		String message = throwable.getMessage();
		if (throwable instanceof FrameworkFunctionalException) {
			// but if we have a function exception
			FrameworkFunctionalException exception = (FrameworkFunctionalException) throwable;
			if (this.messageManager != null && exception.getMessageKey() != null) {
				// and if we have a message manager and a message key
				try {
					// we try to obtain the message with messageManager
					message = this.messageManager.getMessage(exception.getMessageKey(), exception.getMessageArgs());
				} catch (NoSuchMessageException e) {
					// if failed
					if (exception.getMessageArgs() != null && exception.getMessageArgs().length > 0) {
						// we try to apply a format if we have at least one message arg
						message = MessageFormat.format(exception.getMessage(), exception.getMessageArgs());
					}
				}
			}
		}
		return ExceptionResponse.of(status, message);
	}
}
