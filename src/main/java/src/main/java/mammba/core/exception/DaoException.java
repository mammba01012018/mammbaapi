/**
 * DaoException.java - MAMMBA Application
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
public class DaoException extends Exception {

	private static final long serialVersionUID = 6101704076793939374L;

	/**
     * This is called for Dao type of exceptions.
     *
     * @param errorMessage                 specific error message.
     */
	public DaoException(String errorMessage) {
		super(errorMessage);
	}

	/**
     * This is called for Dao type of exceptions with exception object.
     *
     * @param errorMessage                 specific error message.
     * @param e                            exception ref.
     */
    public DaoException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }

}
