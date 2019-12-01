package net.chrisgrollier.cloud.apps.common.exception.handler.support;

import java.util.Objects;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.JsonMappingException;

import net.chrisgrollier.cloud.apps.common.exception.handler.ConstraintViolationExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.handler.ExceptionResponse;
import net.chrisgrollier.cloud.apps.common.exception.handler.JsonMappingExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.handler.ResponseStatusExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.handler.ServiceExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.handler.ServletExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.handler.TypeMismatchExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.handler.ValidationExceptionHandler;
import net.chrisgrollier.cloud.apps.common.exception.service.EntityNotFoundException;
import net.chrisgrollier.cloud.apps.common.exception.service.EntityNotFoundUnrecoverableException;
import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;

/**
 * Basic webapp Exception Handler. Since that class does not hold
 * {@link ControllerAdvice} annotation, it has to be subclassed in your
 * application. The subclass should thus hold {@link ControllerAdvice}
 * annotation.
 * 
 * If localized error message are required, consider inherit
 * {@link DefaultWebAppExceptionHandler}.
 * 
 * @author Atos
 *
 */
public class BasicWebAppExceptionHandler {

	private final JsonMappingExceptionHandler jsonMappingExpHandler;

	private final ConstraintViolationExceptionHandler constraintViolationExpHandler;

	private final ResponseStatusExceptionHandler responseStatusExpHandler;

	private final ServletExceptionHandler servletExpHandler;

	private final ValidationExceptionHandler validationExpHandler;

	private final TypeMismatchExceptionHandler typeMismatchExpHandler;

	private final ServiceExceptionHandler notFoundExceptionHandler;

	@Autowired
	public BasicWebAppExceptionHandler(MessageManager messageManager) {
		super();
		jsonMappingExpHandler = new JsonMappingExceptionHandler();
		constraintViolationExpHandler = new ConstraintViolationExceptionHandler(messageManager);
		responseStatusExpHandler = new ResponseStatusExceptionHandler();
		servletExpHandler = new ServletExceptionHandler();
		validationExpHandler = new ValidationExceptionHandler();
		typeMismatchExpHandler = new TypeMismatchExceptionHandler();
		this.notFoundExceptionHandler = new ServiceExceptionHandler(HttpStatus.NOT_FOUND, messageManager);
	}

	/**
	 * An exception handler dedicated to {@link JsonMappingException}.
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of
	 *         {@link JsonMappingExceptionHandler#handle(JsonMappingException)}
	 */
	@ExceptionHandler(JsonMappingException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleJsonMappingExp(final JsonMappingException exception) {
		return jsonMappingExpHandler.handle(exception);
	}

	/**
	 * An exception handler dedicated to {@link ConstraintViolationException}.
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of
	 *         {@link ConstraintViolationExceptionHandler#handle(ConstraintViolationException)}
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleConstraintViolationExp(final ConstraintViolationException ex) {
		return constraintViolationExpHandler.handle(ex);
	}

	/**
	 * An exception handler dedicated to {@link ResponseStatusException}.
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of
	 *         {@link ResponseStatusExceptionHandler#handle(ResponseStatusException)}
	 */
	@ExceptionHandler(ResponseStatusException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleResponseStatusExp(final ResponseStatusException exception) {
		return responseStatusExpHandler.handle(exception);

	}

	/**
	 * An exception handler dedicated to
	 * {@link HttpMediaTypeNotAcceptableException},
	 * {@link HttpMediaTypeNotSupportedException},
	 * {@link HttpRequestMethodNotSupportedException},
	 * {@link MissingServletRequestParameterException},
	 * {@link MissingServletRequestPartException}, {@link NoHandlerFoundException}
	 * and {@link HttpMessageNotReadableException}.
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return if handled exception is instance of
	 *         {@link HttpMessageNotReadableException} class and
	 *         {@link HttpMessageNotReadableException#getRootCause()} is instance of
	 *         {@link JsonMappingException} class the result of
	 *         {@link JsonMappingExceptionHandler#handle(JsonMappingException)}, if
	 *         handled exception is instance of
	 *         {@link HttpMessageNotReadableException} class and
	 *         {@link HttpMessageNotReadableException#getRootCause()} is <b>not</b>
	 *         instance of {@link JsonMappingException} class a
	 *         {@link ResponseEntity} based on {@link ExceptionResponse} class
	 *         initialized with a http status of 400 (BAD_REQUEST), otherwise the
	 *         result of {@link ServletExceptionHandler#handle(Exception)}
	 */
	// @formatter:off
    @ExceptionHandler({
        HttpMediaTypeNotAcceptableException.class,
        HttpMediaTypeNotSupportedException.class,
        HttpRequestMethodNotSupportedException.class,
        MissingServletRequestParameterException.class,
        MissingServletRequestPartException.class,
        NoHandlerFoundException.class,
        HttpMessageNotReadableException.class
        })
    // @formatter:on
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleServletExp(final Exception exception) {
		if (exception instanceof HttpMessageNotReadableException) {
			final HttpMessageNotReadableException ex = (HttpMessageNotReadableException) exception;
			if (ex.getRootCause() instanceof JsonMappingException) {
				return jsonMappingExpHandler.handle((JsonMappingException) ex.getRootCause());
			}

			final ExceptionResponse response = ExceptionResponse.of(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return servletExpHandler.handle(exception);

	}

	/**
	 * An exception handler dedicated to {@link MethodArgumentNotValidException} and
	 * {@link BindException}.
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of {@link ValidationExceptionHandler#handle(Exception)}
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleValidationExp(final Exception exception) {
		return validationExpHandler.handle(exception);
	}

	/**
	 * An exception handler dedicated to {@link TypeMismatchException} .
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of
	 *         {@link TypeMismatchExceptionHandler#handle(TypeMismatchException)}
	 */
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleTypeMismatchExp(final TypeMismatchException exception) {
		return typeMismatchExpHandler.handle(exception);
	}

	/**
	 * An exception handler dedicated to
	 * {@link EntityNotFoundUnrecoverableException} .
	 * 
	 * @param exception
	 *            then exception to be handled
	 * @return the result of
	 *         {@link ServiceExceptionHandler#handle(EntityNotFoundUnrecoverableException)}
	 */
	@ExceptionHandler({ EntityNotFoundUnrecoverableException.class, EntityNotFoundException.class })
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleBusinessEntityNotFoundException(Exception exception) {
		return notFoundExceptionHandler.handle(exception);
	}

	/**
	 * The default exceptions handler, handle all non caught exceptions.
	 *
	 * @param exception
	 *            the caught Exception
	 * @return an error response entity with a code and a message.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
		if (Objects.nonNull(responseStatus)) {
			httpStatus = responseStatus.value();
		}
		return new ResponseEntity<>(ExceptionResponse.of(httpStatus, exception.getMessage()), httpStatus);
	}

}
