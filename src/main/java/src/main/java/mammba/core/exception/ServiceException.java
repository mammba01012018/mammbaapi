/**
 * ServiceException.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.exception;

/**
 * Service Exception.
 *
 * @author Mardolfh Del Rosario
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -8979061838521568598L;

	/**
     * This is called for Service type of exceptions.
     *
     * @param errorMessage                 specific error message.
     */
	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

	/**
     * This is called for Service type of exceptions with exception object.
     *
     * @param errorMessage                 specific error message.
     * @param e                            exception reference.
     */
    public ServiceException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }

}
