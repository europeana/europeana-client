package eu.europeana.api.client.exception;

/**
 * This class is meant to be used for handling server side errors returned in the response of the Search API  
 * @author Sergiu Gordea
 *
 */
public class EuropeanaApiProblem extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2924749433705930735L;

	private long requestNumber;
	
	public EuropeanaApiProblem(String message, long requestNumber) {
		super(message);
		this.setRequestNumber(requestNumber);
	}

	public EuropeanaApiProblem(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		if(requestNumber < 0)
			return super.getMessage();
		else
			return super.getMessage() + ". RequestNumber: " + getRequestNumber();
	}

	public long getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}
	
}
