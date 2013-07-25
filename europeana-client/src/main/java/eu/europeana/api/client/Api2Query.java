package eu.europeana.api.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import eu.europeana.api.client.adv.EuropeanaFields;

/**
 * 
 * @author Sergiu Gordea
 * 
 */
public class Api2Query extends EuropeanaQuery {

	/**
	 * Constructor that supports searching objects within a collection
	 * @param collectionName
	 */
	public Api2Query(String collectionName) {
		super(EuropeanaFields.EUROPEANA_COLLECTION_NAME + ":" + collectionName);
	}

	public Api2Query() {
		super();
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
}
