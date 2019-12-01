package net.chrisgrollier.cloud.apps.common.exception.service;

import net.chrisgrollier.cloud.apps.common.exception.UnrecoverableFunctionalException;

/**
 * Functional unrecoverable/unchecked exception to be thrown when a business
 * rule was viloated. Typically used in business service layer methods. When
 * checked exception is required, use {@link BusinessRuleViolatedException}.
 * 
 * @author Atos
 *
 */
public class BusinessRuleViolatedUnrecoverableException extends UnrecoverableFunctionalException {

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
	public BusinessRuleViolatedUnrecoverableException(String message, String messageKey, Object... messageArgs) {
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
	public BusinessRuleViolatedUnrecoverableException(String messageKey, Object... messageArgs) {
		super(messageKey, messageKey, messageArgs);
	}

}
