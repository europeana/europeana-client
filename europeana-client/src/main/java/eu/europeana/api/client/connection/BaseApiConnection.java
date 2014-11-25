package eu.europeana.api.client.connection;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;


//TODO: remove the support for V1 and replace the usage of EuropeanaConnection to BaseApiConnection  
public class BaseApiConnection {

	private String apiKey;
	// private String annotationServiceUri =
	// "http://www.europeana.eu/api/v2/search.json";
	private String serviceUri = "";
	private HttpConnector httpConnection = new HttpConnector();
	protected Logger logger = Logger.getLogger(getClass().getName());

	private Gson gson;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getServiceUri() {
		return serviceUri;
	}

	public void setServiceUri(String serviceUri) {
		this.serviceUri = serviceUri;
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
	 * @param apiKey
	 *            API Key required to access the API
	 */
	public BaseApiConnection(String serviceUri, String apiKey) {
		this.apiKey = apiKey;
		this.serviceUri = serviceUri;
	}

	
	String getJSONResult(String url) throws IOException {
		logger.trace("Call to Annotation API (GET): " + url);
		return getHttpConnection().getURLContent(url);
	}
	
	String getJSONResult(String url, String paramName, String jsonPost) throws IOException {
		logger.trace("Call to Annotation API (POST): " + url);
		return getHttpConnection(). getURLContent(url, paramName, jsonPost);
	}
}
