package net.chrisgrollier.cloud.apps.common.exception;

/**
 * Unchecked exception that should wraps all technical errors.
 */
public class TechnicalException extends RuntimeException implements FrameworkException {

	private static final long serialVersionUID = 2624019012795030013L;

	/**
	 * Create a new instance for the given message and error cause.
	 * 
	 * @param message
	 *            the description of the failure
	 * @param cause
	 *            the underlying cause of the exception
	 */
	public TechnicalException(final String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create a new instance for the given error message.
	 * 
	 * @param message
	 *            the description of the failure
	 */
	public TechnicalException(String message) {
		super(message);
	}

	/**
	 * Create a new instance for the error cause.
	 * 
	 * @param cause
	 *            the underlying cause of the exception
	 */
	public TechnicalException(Throwable cause) {
		super(cause);
	}

}
