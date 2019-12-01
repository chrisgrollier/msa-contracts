package net.chrisgrollier.cloud.apps.common.exception.service;

import net.chrisgrollier.cloud.apps.common.exception.FunctionalException;

/**
 * Functional checked exception to be thrown when a business entity was searched
 * but not found. Typically used in business service layer "find*" or "update*"
 * methods. When unchecked exception is required, use
 * {@link EntityNotFoundUnrecoverableException}.
 * 
 * @author Atos
 *
 */
public class EntityNotFoundException extends FunctionalException {

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
	public EntityNotFoundException(String message, Throwable cause, String messageKey, Object... messageArgs) {
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
	public EntityNotFoundException(String message, String messageKey, Object... messageArgs) {
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
	public EntityNotFoundException(Throwable cause, String messageKey, Object... messageArgs) {
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
	public EntityNotFoundException(String messageKey, Object... messageArgs) {
		super(messageKey, messageKey, messageArgs);
	}
}
