package eu.europeana.api.client;

import eu.europeana.api.client.connection.EuropeanaConnection;

public class ProviderDatasetsClient extends EuropeanaConnection {

	/**
	 * Default constructor which calls the default constructor of the parent 
	 * EuropeanaConnection class
	 */
	public ProviderDatasetsClient(){
		super();
	}
	
	/**
	 * Constructor which provides new strings for the search URI and
	 * and the API key.
	 *  
	 * @param baseApiUri: Base URI for the Europeana api request (see config file).
	 * @param apiKey: API key for the Europeana api request (see config file).
	 */
	public ProviderDatasetsClient(String baseApiUri, String apiKey){
		super(baseApiUri, apiKey);
	}

}
