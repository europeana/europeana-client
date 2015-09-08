package eu.europeana.api.client.content;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.metadata.MetadataAccessor;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * @deprecated use {@link MetadataAccessor}
 * @author Sergiu Gordea 
 *
 */
public class ContentAccessor extends MetadataAccessor{

	
	public ContentAccessor(Api2QueryInterface query,
			EuropeanaApi2Client apiClient) {
		
		super(query, apiClient);
	}

	

	
	
	
	
	
}
