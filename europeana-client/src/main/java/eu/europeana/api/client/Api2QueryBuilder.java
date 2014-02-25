package eu.europeana.api.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class Api2QueryBuilder {

	public Api2QueryInterface buildQuery(String collectionName,
			String generalTerms, String what) throws IOException {

		return buildQuery(collectionName, generalTerms, what, null);
	}

	
	public Api2QueryInterface buildQuery(String collectionName,
			String generalTerms, String what, String objectType) throws IOException {
		
		return buildQuery(collectionName, generalTerms, what, null, objectType); 
	}
	
	public Api2QueryInterface buildQuery(String collectionName,
			String generalTerms, String what, String creator, String objectType){

		return buildQuery(collectionName, generalTerms, what, creator,
				objectType, null);
	}

	public Api2QueryInterface buildQuery(String collectionName,
			String generalTerms, String what, String creator,
			String objectType, String provider) {

		return buildQuery(collectionName, generalTerms, what, creator,
				objectType, provider, null);
	}

	public Api2QueryInterface buildQuery(String collectionName,
			String generalTerms, String what, String creator,
			String objectType, String provider, String dataProvider) {

		return buildQuery(collectionName, generalTerms, what, creator,
				objectType, provider, dataProvider, null);
	}

	public Api2QueryInterface buildQuery(String collectionName,

	String generalTerms, String what, String creator, String objectType,
			String provider, String dataProvider, String[] refinements) {
		Api2QueryInterface query = new Api2Query(collectionName);

		query.setWhatTerms(what);
		query.setGeneralTerms(generalTerms);
		query.setCreator(creator);
		query.setType(objectType);
		query.setProvider(provider);
		query.setDataProvider(dataProvider);

		if (refinements != null && refinements.length > 0) {
			for (int i = 0; i < refinements.length; i++) {
				query.addQueryRefinement(refinements[i]);
			}
		}
		return query;
	}

	
	public Api2QueryInterface buildQuery(String portalSearchUrl) throws MalformedURLException, UnsupportedEncodingException {
		
		URL portalUrl = new URL(portalSearchUrl); 
		Api2Query apiQuery = new Api2Query();
		
		String queryParams = portalUrl.getQuery();
		queryParams = removeStartRows(queryParams);
		
		apiQuery.setQueryParams(queryParams);
		
		return apiQuery;
	}
	
	private String removeStartRows(String queryParams) {
		int startPos = queryParams.indexOf("&start=");
		int rowsPos = queryParams.indexOf("&rows=");
		//start=&rows= are the last parameters in the portal queries
		if (startPos > 0)
			return queryParams.substring(0, startPos);
		else if(rowsPos > 0)
			return queryParams.substring(0, rowsPos);
		else
			return queryParams;
	}
}
