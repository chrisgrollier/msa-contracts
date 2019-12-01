package net.chrisgrollier.cloud.apps.common.exception;

/**
 * Unchecked exception that may wraps all non recoverable business errors.
 */
public class UnrecoverableFunctionalException extends RuntimeException implements FrameworkFunctionalException {

	private static final long serialVersionUID = 2624019012795030013L;

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
	public UnrecoverableFunctionalException(final String message, Throwable cause) {
		this(message, cause, null);
	}

	/**
	 * Create a new instance for the given error message.
	 * 
	 * @param message
	 *            the description of the failure
	 */
	public UnrecoverableFunctionalException(String message) {
		this(message, (String) null);
	}

	/**
	 * Create a new instance for the error cause.
	 * 
	 * @param cause
	 *            the underlying cause of the exception
	 */
	public UnrecoverableFunctionalException(Throwable cause) {
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
	public UnrecoverableFunctionalException(final String message, Throwable cause, String messageKey,
			Object... messageArgs) {
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
	public UnrecoverableFunctionalException(String message, String messageKey, Object... messageArgs) {
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
	public UnrecoverableFunctionalException(Throwable cause, String messageKey, Object... messageArgs) {
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
