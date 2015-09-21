package eu.europeana.api.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.connection.EuropeanaConnection;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.model.EuropeanaObjectResponse;
import eu.europeana.api.client.model.search.EuropeanaObject;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;
import eu.europeana.api.client.search.query.EuropeanaQueryInterface;


/**
 * The main class used for accessing the Europeana Search API - V2
 * 
 * @author Sergiu Gordea 
 * @version 1.0
 * 
 * EuropeanaApi2Client class accesses the Search API V2 by setting up a connection via 
 * search URI and API key (see also config file). Furthermore, it sends search requests, 
 * retrieves results and gets objects from the search service. 
 */
public class EuropeanaApi2Client extends EuropeanaConnection {
	private String jsonResult = "";
	private EuropeanaObjectResponse objects;
	private Api2QueryBuilder queryBuilder;

	/**
	 * Method for creation of an Api2QueryBuilder
	 * 
	 * @return Api2QueryBuilder: object for generation of a new query.
	 */
	public Api2QueryBuilder getQueryBuilder() {
		if(queryBuilder == null)
			queryBuilder = new Api2QueryBuilder();
		return queryBuilder;
	}

	/**
	 * Default constructor which calls the default constructor of the
	 * EuropeanaConnection class which is the parent class of 
	 * EuropeanaApi2Client.
	 */
	public EuropeanaApi2Client(){
		super();
	}
	
	/**
	 * Constructor which provides new strings for the search URI and
	 * and the API key.
	 *  
	 * @param europeanaSearchUri: Search URI for the Europeana search request (see config file).
	 * @param apiKey: API key for the Europeana search request (see config file).
	 */
	public EuropeanaApi2Client(String europeanaSearchUri, String apiKey){
		super(europeanaSearchUri, apiKey);
	}

	/**
	 * Method for remote invocation of Europeana Search API, Version 2
	 * 
	 * This method takes a query interface object and the limit and start of a query
	 * to generate a URL and use it to get search results from the search API by
	 * calling the {@link #getSearchResults(String)} method.
	 * 
	 * @param query @see Api2Query: a query object representing the expected results.
	 * @param limit: the limit for the amount of results retrieved.
	 * @param start: the starting index for the query results.
	 * @return The return value is a EuropeanaApi2Results object containing the search results.
	 * @throws IOException
	 * @throws EuropeanaApiProblem 
	 */
	public EuropeanaApi2Results searchApi2(EuropeanaQueryInterface query, int limit, int start) throws IOException, EuropeanaApiProblem {
		
		//String cadenaBusq = search.getSearchTerms();
		String url = query.getQueryUrl(this, limit, start);
        return getSearchResults(url);
	}

	/**
	 * Method for search results from Europeana Search API, Version 2
	 * 
	 * The method retrieves a json result from the provided URL and
	 * generates a EuropeanaApi2Results object based on the json data.
	 * 
	 * @param url: URL for the search request.
	 * @return generated object from json data.
	 * @throws IOException
	 * @throws EuropeanaApiProblem
	 */
	protected EuropeanaApi2Results getSearchResults(String url)
			throws IOException, EuropeanaApiProblem {
		// Execute Europeana API request
        this.jsonResult = getJSONResult(url);
		
        return parseApiResponse(jsonResult);
	}

	public EuropeanaApi2Results parseApiResponse(String jsonResult)
			throws EuropeanaApiProblem {
		// Load results object from JSON
        Gson gson = new GsonBuilder().create();
        EuropeanaApi2Results res = gson.fromJson(jsonResult, EuropeanaApi2Results.class);
        
        if(!res.getSuccess())
        	throw new EuropeanaApiProblem(res.getError(), res.getRequestNumber());
        
        return res;
	} 
	
	/**
	 * Method for remote invocation of Europeana Search API, Version 2
	 * 
	 * This method takes a portal search url string and the limit and start of a query
	 * to generate a query interface object and URL and use them to get search results 
	 * from the search API by calling the {@link #getSearchResults(String)} method.
	 * 
	 * @param portalSearchUrl: search URL for the portal search.
	 * @param limit: the limit for the amount of results retrieved.
	 * @param start: the starting index for the query results.
	 * @return The return value is a EuropeanaApi2Results object containing the search results.
	 * @throws IOException
	 * @throws EuropeanaApiProblem 
	 */
	public EuropeanaApi2Results searchApi2(String portalSearchUrl, int limit, int start) throws IOException, EuropeanaApiProblem{
		Api2QueryInterface query = getQueryBuilder().buildQuery(portalSearchUrl);
		String queryUrl = query.getQueryUrl(this, limit, start);
		
		return getSearchResults(queryUrl);
	}
	
	/**
	 * Method for retrieval of a EuropeanaObject
	 * 
	 * The method uses the client configuration to generate a url and
	 * retrieve json results. The json results are used to generate a full
	 * EuropeanaObject which is then return by the method.
	 * 
	 * @param id: id of the new object.
	 * @return EuropeanaObject which represents a full result object of the search.
	 * @throws IOException
	 */
	public EuropeanaObject getObject(String id) throws IOException {
		EuropeanaObject result = null;
		getEuropeanaRecordResponse(id);
		
		Gson gson = new GsonBuilder().create();
		this.objects = gson.fromJson(this.jsonResult, EuropeanaObjectResponse.class);
		result = this.objects.getObject();
		
		return result;
	}

	public String getEuropeanaRecordResponse(String id) throws IOException {
		return getEuropeanaRecordResponse(id, null);
	}
	
	
	public String getEuropeanaRecordResponse(String id, String profile) throws IOException {
		String record_url =  buildObjectAccessUrl(id, profile);
		this.jsonResult = getJSONResult(record_url);
		return this.jsonResult;
	}

	protected String buildObjectAccessUrl(String id, String profile) {
		StringBuilder builder = new StringBuilder();
		builder.append(ClientConfiguration.getInstance().getRecordUri());
		builder.append(id).append(".json");
		builder.append(buildApiKeyParam());
		if(profile != null)
			builder.append("&profile=").append(profile);
		
		return builder.toString();
	}
	
	protected String buildApiKeyParam() {
		return "?wskey=" + getApiKey();
	}
}
