package net.chrisgrollier.cloud.apps.common.exception;

/**
 * Interface for functional / business exceptions. Those exceptions may be
 * associated to a resource property @link {@link #getMessageKey()} in a
 * resource bundle, which property could refer to a message that requires
 * parameters/arguments {@link #getMessageArgs()}.
 * 
 * @author Atos
 *
 */
public interface FrameworkFunctionalException extends FrameworkException {

	/**
	 * Return the message resource key, if any
	 * 
	 * @return the message resource key, if any
	 */
	String getMessageKey();

	/**
	 * Return message arguments to be applied to the resource related to the message
	 * key.
	 * 
	 * @return message arguments to be applied to the resource related to the
	 *         message key
	 * @see #getMessageKey()
	 */
	Object[] getMessageArgs();

}
