package eu.europeana.api.client.exception;

/**
 * This class is base class for Exceptions thrown by client side processing tasks  
 * @author Sergiu Gordea
 *
 */
public class EuropeanaClientException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	public EuropeanaClientException(String message, Throwable th) {
		super(message, th);

	}

	public EuropeanaClientException(String message) {
		super(message);
	}
	
}
