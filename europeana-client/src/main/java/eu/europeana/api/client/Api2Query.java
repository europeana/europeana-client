package eu.europeana.api.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import eu.europeana.api.client.connection.EuropeanaConnection;
import eu.europeana.api.common.EuropeanaFields;

/**
 * 
 * @author Sergiu Gordea
 * 
 */
public class Api2Query extends EuropeanaQuery implements Api2QueryInterface {

	private List<String> queryRefinements;
    
	/**
	 * Constructor that supports searching objects within a collection
	 * @param collectionName
	 */
	public Api2Query(String collectionName) {
		super(EuropeanaFields.EUROPEANA_COLLECTION_NAME + IS + collectionName);
	}

	public Api2Query() {
		super();
	}
	
	public List<String> getQueryRefinements() {
		return queryRefinements;
	}

	public void addQueryRefinement(String qf) {
		if(queryRefinements == null)
			queryRefinements = new ArrayList<String>(3);
		
		queryRefinements.add(qf);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see eu.europeana.api.client.EuropeanaQuery#getQueryUrl(eu.europeana.api.client.EuropeanaConnection, long, long)
	 */
	public String getQueryUrl(EuropeanaConnection connection, long limit,
			long offset) throws UnsupportedEncodingException {
		String searchTerms = getSearchTerms();
		// connection.setEuropeanaUri("http://api.europeana.eu/api/opensearch.json");
		StringBuilder url = new StringBuilder();
		url.append(connection.getEuropeanaUri()).append("?query=")
				.append(URLEncoder.encode(searchTerms, "UTF-8"));
		if (limit > 0)
			url.append("&rows=").append(limit);
		if (offset > 0)
			url.append("&start=").append(offset);
		url.append("&wskey=").append(connection.getApiKey());
		return url.toString();
	}
	
	@Override
	protected void buildSearchQueryString(StringBuffer buf) {
		super.buildSearchQueryString(buf);
		
		if(getQueryRefinements() != null){
			for (String qf : getQueryRefinements()) {
				appendQueryRefinement(buf, null, qf);
			}
		}
			
	}
	
	private void appendQueryRefinement(StringBuffer buf, String facetField, String qf) {
    	if (qf == null) {
            return;
        }

    	qf = qf.trim();
        if (qf.length() == 0) {
            return;
        }
        
        buf.append("&qf=");
        
        if(facetField != null && !facetField.trim().isEmpty())
        	buf.append(facetField).append(IS);
        
        buf.append(qf);
	}

	
}
