package eu.europeana.api.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EuropeanaApi2Client extends EuropeanaConnection {

	public EuropeanaApi2Client(){
		super();
	}
	
	public EuropeanaApi2Client(String europeanaSearchUri, String apiKey){
		super(europeanaSearchUri, apiKey);
	}

	public EuropeanaApi2Results searchApi2(Api2Query query, int limit, int start) throws IOException {
		
		//String cadenaBusq = search.getSearchTerms();
		String url = query.getQueryUrl(this, limit, start);
        // Execute Europeana API request
        String json = getJSONResult(url);
		
        // Load results object from JSON
        Gson gson = new GsonBuilder().create();
        EuropeanaApi2Results res = gson.fromJson(json, EuropeanaApi2Results.class);

        return res;

	} 
	
	
	
	
	
}
