package net.chrisgrollier.cloud.apps.common.exception.service;

import net.chrisgrollier.cloud.apps.common.exception.FunctionalException;

/**
 * Functional checked exception to be thrown when a business rule was violated.
 * Typically used in business service layer methods. When unchecked exception is
 * required, use {@link BusinessRuleViolatedUnrecoverableException}.
 * 
 * @author Atos
 *
 */
public class BusinessRuleViolatedException extends FunctionalException {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 4030272966025092548L;

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
	public BusinessRuleViolatedException(String message, String messageKey, Object... messageArgs) {
		super(message, messageKey, messageArgs);
	}

	/**
	 * Creates a new instance with given message key and optional arguments.
	 * 
	 * @param messageKey
	 *            a property key for a localized message
	 * @param messageArgs
	 *            optional arguments to be passed/applied on the localized message
	 */
	public BusinessRuleViolatedException(String messageKey, Object... messageArgs) {
		super(messageKey, messageKey, messageArgs);
	}

}
