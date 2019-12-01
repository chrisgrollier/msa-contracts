
package net.chrisgrollier.cloud.apps.common.exception;

/**
 * Checked exception that may wraps all recoverable business errors.
 */
public class FunctionalException extends Exception implements FrameworkFunctionalException {

	private static final long serialVersionUID = -8904876045802336263L;

	/** message resource key */
	private final String messageKey;

	/** message resource arguments */
	private final Object[] messageArgs;

	/**
	 * Create a new instance for the given message and error cause.
	 * 
	 * @param message
	 *            the description of the failure
	 * @param cause
	 *            the underlying cause of the exception
	 */
	public FunctionalException(final String message, Throwable cause) {
		this(message, cause, null);
	}

	/**
	 * Create a new instance for the given error message.
	 * 
	 * @param message
	 *            the description of the failure
	 */
	public FunctionalException(String message) {
		this(message, (String) null);
	}

	/**
	 * Create a new instance for the error cause.
	 * 
	 * @param cause
	 *            the underlying cause of the exception
	 */
	public FunctionalException(Throwable cause) {
		this(cause, null);
	}

	/**
	 * Create a new instance for the given message and error cause.
	 * 
	 * @param message
	 *            the description of the failure
	 * @param cause
	 *            the underlying cause of the exception
	 * @param messageKey
	 *            a message resource key
	 * @param messageArgs
	 *            an array of message resource arguments
	 */
	public FunctionalException(final String message, Throwable cause, String messageKey, Object... messageArgs) {
		super(message, cause);
		this.messageKey = messageKey;
		this.messageArgs = messageArgs;
	}

	/**
	 * Create a new instance for the given error message.
	 * 
	 * @param message
	 *            the description of the failure
	 * @param messageKey
	 *            a message resource key
	 * @param messageArgs
	 *            an array of message resource arguments
	 */
	public FunctionalException(String message, String messageKey, Object... messageArgs) {
		super(message);
		this.messageKey = messageKey;
		this.messageArgs = messageArgs;
	}

	/**
	 * Create a new instance for the error cause.
	 * 
	 * @param cause
	 *            the underlying cause of the exception
	 * @param messageKey
	 *            a message resource key
	 * @param messageArgs
	 *            an array of message resource arguments
	 */
	public FunctionalException(Throwable cause, String messageKey, Object... messageArgs) {
		super(cause);
		this.messageKey = messageKey;
		this.messageArgs = messageArgs;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getMessageArgs() {
		return messageArgs;
	}
}
