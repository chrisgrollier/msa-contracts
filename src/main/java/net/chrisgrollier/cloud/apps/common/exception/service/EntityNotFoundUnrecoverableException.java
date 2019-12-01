package net.chrisgrollier.cloud.apps.common.exception.service;

import net.chrisgrollier.cloud.apps.common.exception.UnrecoverableFunctionalException;

/**
 * Functional unrecoverable/unchecked exception to be thrown when a business
 * entity was searched but not found. Typically used in business service layer
 * "find*" or "update*" methods. When checked exception is required, use
 * {@link EntityNotFoundException}.
 * 
 * @author Atos
 *
 */
public class EntityNotFoundUnrecoverableException extends UnrecoverableFunctionalException {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 4030272966025092548L;

	/**
	 * Creates a new instance with given message, cause, message key and optional
	 * arguments.
	 * 
	 * @param message
	 *            a unlocalized message
	 * @param cause
	 *            the exception that triggered this new one
	 * @param messageKey
	 *            a property key for a localized message
	 * @param messageArgs
	 *            optional arguments to be passed/applied on the localized message
	 */
	public EntityNotFoundUnrecoverableException(String message, Throwable cause, String messageKey,
			Object... messageArgs) {
		super(message, cause, messageKey, messageArgs);
	}

	/**
	 * Creates a new instance with given message, message key and optional
	 * arguments.
	 * 
	 * @param message
	 *            a unlocalized message
	 * @param messageKey
	 *            a property key for a localized message
	 * @param messageArgs
	 *            optional arguments to be passed/applied on the localized message
	 */
	public EntityNotFoundUnrecoverableException(String message, String messageKey, Object... messageArgs) {
		super(message, messageKey, messageArgs);
	}

	/**
	 * Creates a new instance with given cause, message key and optional arguments.
	 * 
	 * @param cause
	 *            the exception that triggered this new one
	 * @param messageKey
	 *            a property key for a localized message
	 * @param messageArgs
	 *            optional arguments to be passed/applied on the localized message
	 */
	public EntityNotFoundUnrecoverableException(Throwable cause, String messageKey, Object... messageArgs) {
		super(messageKey, cause, messageKey, messageArgs);
	}

	/**
	 * Creates a new instance with given message key and optional arguments.
	 * 
	 * @param messageKey
	 *            a property key for a localized message
	 * @param messageArgs
	 *            optional arguments to be passed/applied on the localized message
	 */
	public EntityNotFoundUnrecoverableException(String messageKey, Object... messageArgs) {
		super(messageKey, messageKey, messageArgs);
	}
}
