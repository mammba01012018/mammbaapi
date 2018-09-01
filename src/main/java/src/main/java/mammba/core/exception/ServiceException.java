package src.main.java.mammba.core.exception;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979061838521568598L;
	
	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

}
