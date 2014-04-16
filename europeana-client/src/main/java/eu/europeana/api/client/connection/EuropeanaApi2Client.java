package eu.europeana.api.client.connection;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.europeana.api.client.Api2QueryBuilder;
import eu.europeana.api.client.Api2QueryInterface;
import eu.europeana.api.client.EuropeanaQueryInterface;
import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.result.EuropeanaApi2Results;
import eu.europeana.api.client.result.EuropeanaObject;
import eu.europeana.api.client.result.EuropeanaObjects;



public class EuropeanaApi2Client extends EuropeanaConnection {
	private String jsonResult = "";
	private EuropeanaObjects objects;
	private Api2QueryBuilder queryBuilder;

	public Api2QueryBuilder getQueryBuilder() {
		if(queryBuilder == null)
			queryBuilder = new Api2QueryBuilder();
		return queryBuilder;
	}

	public EuropeanaApi2Client(){
		super();
	}
	
	public EuropeanaApi2Client(String europeanaSearchUri, String apiKey){
		super(europeanaSearchUri, apiKey);
	}

	/**
	 * Method for remote invocation of Europeana Search API, Version 2
	 * 
	 * @param query @see Api2Query
	 * @param limit
	 * @param start
	 * @return
	 * @throws IOException
	 * @throws EuropeanaApiProblem 
	 */
	public EuropeanaApi2Results searchApi2(EuropeanaQueryInterface query, int limit, int start) throws IOException, EuropeanaApiProblem {
		
		//String cadenaBusq = search.getSearchTerms();
		String url = query.getQueryUrl(this, limit, start);
        return getSearchResults(url);
	}

	protected EuropeanaApi2Results getSearchResults(String url)
			throws IOException, EuropeanaApiProblem {
		// Execute Europeana API request
        this.jsonResult = getJSONResult(url);
		
        // Load results object from JSON
        Gson gson = new GsonBuilder().create();
        EuropeanaApi2Results res = gson.fromJson(this.jsonResult, EuropeanaApi2Results.class);
        
        if(!res.getSuccess())
        	throw new EuropeanaApiProblem(res.getError(), res.getRequestNumber());
        
        return res;
	} 
	
	public EuropeanaApi2Results searchApi2(String portalSearchUrl, int limit, int start) throws IOException, EuropeanaApiProblem{
		Api2QueryInterface query = getQueryBuilder().buildQuery(portalSearchUrl);
		String queryUrl = query.getQueryUrl(this, limit, start);
		
		return getSearchResults(queryUrl);
	}
	
	public EuropeanaObject getObject(String id) throws IOException {
		EuropeanaObject result = null;
		String record_url =  ClientConfiguration.getInstance().getRecordUri() + id + ".json?wskey=" + ClientConfiguration.getInstance().getApiKey();
		this.jsonResult = getJSONResult(record_url);
		
		Gson gson = new GsonBuilder().create();
		this.objects = gson.fromJson(this.jsonResult, EuropeanaObjects.class);
		result = this.objects.getObject();
		
		return result;
	}
	
}
