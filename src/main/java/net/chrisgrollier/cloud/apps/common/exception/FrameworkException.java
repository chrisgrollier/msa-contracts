package net.chrisgrollier.cloud.apps.common.exception;

/**
 * A simple interface which main purpose is to identify framework exceptions
 * (TechnicalException, FunctionalException and
 * UnrecoverableFunctionalException) and their subclasses.
 */
public interface FrameworkException {

	/**
	 * A simple accessor to exception message.
	 * 
	 * @return exception message.
	 */
	String getMessage();

}
