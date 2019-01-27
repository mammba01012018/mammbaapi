/**
 * ControllerException.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.exception;

/**
 * Controller Exception.
 *
 * @author Mardolfh Del Rosario
 *
 */
public class ControllerException extends Exception {

	private static final long serialVersionUID = -2844984119625910803L;

	/**
	 * This is called for Controller type of exceptions.
	 *
	 * @param errorMessage                 specific error message.
	 */
	public ControllerException(String errorMessage) {
		super(errorMessage);
	}

	/**
     * This is called for Controller type of exceptions having exception ref parameter.
     *
     * @param e                 exception ref.
     */
    public ControllerException(Exception e) {
        super(e);
    }

}
