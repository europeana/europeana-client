package eu.europeana.api.client.connection;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.europeana.api.client.EuropeanaQueryInterface;
import eu.europeana.api.client.result.EuropeanaApi2Results;
import eu.europeana.api.client.result.EuropeanaObject;
import eu.europeana.api.client.result.EuropeanaObjects;



public class EuropeanaApi2Client extends EuropeanaConnection {
	private String jsonResult = "";
	private EuropeanaObjects objects;

	public EuropeanaApi2Client(){
		super();
	}
	
	public EuropeanaApi2Client(String europeanaSearchUri, String apiKey){
		super(europeanaSearchUri, apiKey);
	}

	public EuropeanaApi2Results searchApi2(EuropeanaQueryInterface query, int limit, int start) throws IOException {
		
		//String cadenaBusq = search.getSearchTerms();
		String url = query.getQueryUrl(this, limit, start);
        // Execute Europeana API request
        this.jsonResult = getJSONResult(url);
		
        // Load results object from JSON
        Gson gson = new GsonBuilder().create();
        EuropeanaApi2Results res = gson.fromJson(this.jsonResult, EuropeanaApi2Results.class);

        return res;

	} 
	
	public EuropeanaObject getObject(String id) throws IOException {
		EuropeanaObject result = null;
		
		Gson gson = new GsonBuilder().create();
		this.objects = gson.fromJson(this.jsonResult, EuropeanaObjects.class);
		for(EuropeanaObject o : this.objects.getItems()) {
			String json = getJSONResult(o.getLink());
			result = gson.fromJson(json, EuropeanaObjects.class).getObject();
			if(result.getAbout().equals(id))
				return result;
		}
		
		return null;
	}
	
}
