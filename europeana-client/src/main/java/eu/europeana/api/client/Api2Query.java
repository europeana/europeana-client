package eu.europeana.api.client;

import java.io.UnsupportedEncodingException;
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
	private String collectionName;
    
	/**
	 * Constructor that supports searching objects within a collection
	 * @param collectionName
	 */
	public Api2Query(String collectionName) {
		this.setCollectionName(collectionName);
	}

	public Api2Query() {
		super();
	}
	
	@Override
	public List<String> getQueryRefinements() {
		return queryRefinements;
	}

	@Override
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
		searchTerms = encodeSearchTerms(searchTerms);
				
		StringBuilder url = new StringBuilder();
		
		url.append(connection.getEuropeanaUri()).append("?query=");
		url.append(searchTerms);
		
		appendQueryRefinements(url);
		
		if (limit > 0)
			url.append("&rows=").append(limit);
		if (offset > 0)
			url.append("&start=").append(offset);
		url.append("&wskey=").append(connection.getApiKey());
		return url.toString();
	}

	void appendQueryRefinements(StringBuilder url) throws UnsupportedEncodingException {
		
		if(getQueryRefinements() == null)
			return;
		
		for (String qf : getQueryRefinements()) {
			appendQueryRefinement(url, qf);
		}
		
	}
	
	private void appendQueryRefinement(StringBuilder buf, String qf) throws UnsupportedEncodingException {
    	if (qf == null) {
            return;
        }

    	qf = qf.trim();
        if (qf.length() == 0) {
            return;
        }
        
        buf.append("&qf=");
        
//        if(facetField != null && !facetField.trim().isEmpty())
//        	buf.append(facetField).append(IS);
        
        buf.append(encodeSearchTerms(qf));
	}

	@Override
	protected void buildSearchQueryString(StringBuffer buf) {
		super.buildSearchQueryString(buf);
		
		if(getCollectionName() !=null)
			addSearchField(buf, EuropeanaFields.EUROPEANA_COLLECTION_NAME, collectionName);
	}
	
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	
}
