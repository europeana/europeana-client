package eu.europeana.api.client.myeuropeana.exception;

import eu.europeana.api.client.exception.EuropeanaApiProblem;

public class MyEuropeanaApiException extends EuropeanaApiProblem {

	public MyEuropeanaApiException(String message) {
		super(message);
	}

	public MyEuropeanaApiException(String message, long requestNumber) {
		super(message, requestNumber);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6261502794132899462L;

}
