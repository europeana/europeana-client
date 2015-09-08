package eu.europeana.api.client.connection;

import java.io.IOException;

import org.apache.log4j.Logger;

import eu.europeana.api.client.config.ClientConfiguration;

//TODO: remove the support for V1 and replace the usage of EuropeanaConnection to BaseApiConnection  
public class BaseApiConnection {

	private String apiKey;
	// private String annotationServiceUri =
	// "http://www.europeana.eu/api/v2/search.json";
	private String baseServiceUri = "";
	private HttpConnector httpConnection = new HttpConnector();
	protected Logger logger = Logger.getLogger(getClass().getName());

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getServiceUri() {
		return baseServiceUri;
	}

	public void setServiceUri(String serviceUri) {
		this.baseServiceUri = serviceUri;
	}

	public HttpConnector getHttpConnection() {
		return httpConnection;
	}

	public void setHttpConnection(HttpConnector httpConnection) {
		this.httpConnection = httpConnection;
	}

	/**
	 * Create a new connection to the Annotation Service (REST API).
	 * 
	 * @param baseServiceUri
	 * 
	 * @param apiKey
	 *            API Key required to access the API
	 * 
	 */
	public BaseApiConnection(String baseServiceUri, String apiKey) {
		this.apiKey = apiKey;
		this.baseServiceUri = baseServiceUri;
	}

	/**
	 * Create a new connection to the Annotation Service (REST API) using the default configuration in the properties files
	 */
	public BaseApiConnection() {
		this(
				//ClientConfiguration.getInstance().getSearchUri(),
				ClientConfiguration.getInstance().getEuropeanaUri(),
				ClientConfiguration.getInstance().getApiKey());
	}

	protected String getJSONResult(String url) throws IOException {
		logger.trace("Call to Annotation API (GET): " + url);
		return getHttpConnection().getURLContent(url);
	}

	protected String getJSONResult(String url, String paramName, String jsonPost)
			throws IOException {
		logger.trace("Call to Annotation API (POST): " + url);
		
		return getHttpConnection().getURLContent(url, paramName, jsonPost);
	}
	
//	protected String buildApiKeyParam() {
//		return "?wskey=" + getApiKey();
//	}
	
	
}
