package net.chrisgrollier.cloud.apps.common.exception.handler.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chrisgrollier.cloud.apps.common.exception.FunctionalException;
import net.chrisgrollier.cloud.apps.common.exception.UnrecoverableFunctionalException;
import net.chrisgrollier.cloud.apps.common.exception.handler.ExceptionResponse;
import net.chrisgrollier.cloud.apps.common.exception.handler.ServiceExceptionHandler;
import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;

/**
 * Default webapp Exception Handler. It extends
 * {@link BasicWebAppExceptionHandler}, adding an i18n aware handler for
 * {@link FunctionalException} and {@link UnrecoverableFunctionalException} that
 * can produce localized error messages. Since that class does not hold
 * {@link ControllerAdvice} annotation, it has to be subclassed in your
 * application. The subclass should thus hold {@link ControllerAdvice}
 * annotation.
 * 
 * @author Atos
 *
 */
public class DefaultWebAppExceptionHandler extends BasicWebAppExceptionHandler {

	private final ServiceExceptionHandler functionalExceptionHandler;

	@Autowired
	public DefaultWebAppExceptionHandler(MessageManager messageManager) {
		super(messageManager);
		this.functionalExceptionHandler = new ServiceExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR, messageManager);
	}

	/**
	 * An exception handler dedicated to {@link UnrecoverableFunctionalException}
	 * and {@link FunctionalException} .
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of {@link ServiceExceptionHandler#handle(Exception)}
	 */
	@ExceptionHandler({ UnrecoverableFunctionalException.class, FunctionalException.class })
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleFunctionalException(Exception exception) {
		return functionalExceptionHandler.handle(exception);
	}
}
