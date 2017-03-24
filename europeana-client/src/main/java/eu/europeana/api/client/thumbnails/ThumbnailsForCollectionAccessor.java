package eu.europeana.api.client.thumbnails;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.search.query.Api2Query;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * A ThumbnailsAccesor is a tool that makes easier the handling of thumbnails of
 * the Europeana items.
 * 
 * @author Andres Viedma
 * @version 1.0
 * 
 * The class provides functionality to copy thumbnails and write a thumbnail to
 * a folder.
 */
public class ThumbnailsForCollectionAccessor extends ThumbnailsAccessor{

	private Log logger = LogFactory.getLog(ThumbnailsForCollectionAccessor.class);
	
	public ThumbnailsForCollectionAccessor(String collectionName){
		this(collectionName, null);
	}
		
	
	public ThumbnailsForCollectionAccessor(String collectionName, EuropeanaApi2Client apiClient){
		
		this(new Api2Query(collectionName), apiClient);
	}


	public ThumbnailsForCollectionAccessor(Api2QueryInterface query,
			EuropeanaApi2Client apiClient) {
		
		super(apiClient);
		
		setQuery(query);
		//setBlockSize(DEFAULT_BLOCKSIZE);
		results = new LinkedHashMap<String, String>(getBlockSize());
	}
	
	
	/**
	 * This method extracts the map of <thumbnailId, thumbnailURL> by invoking the search API.
	 * If available the URL of the LARGE version of thumbnails is returned, otherwise the first available   
	 * 
	 * If <code>limit > DEFAULT_BLOCKSIZE</code>, the thumbnails will be be fetched iteratively in DEFAULT_BLOCKSIZE chuncks
	 * 
	 * @param start - first position in results. if smaller than 0, this parameter will default to 1
	 * @param limit - the number or returned results. If <code>start + limit > totalResults</code>, the (last) available result starting with the start position will be returned
	 * @return
	 * @throws EuropeanaApiProblem 
	 */
	public Map<String, String> getThumbnailsForCollection(int start, int limit, int errorHandlingPolicy) 
			throws TechnicalRuntimeException, IOException, EuropeanaApiProblem {
		return getThumbnailsForCollection(CommonMetadata.EDM_FIELD_THUMBNAIL_LARGE, start, limit, errorHandlingPolicy);		
	}

	/**
	 * This method extracts the map of <thumbnailId, thumbnailURL> by invoking the search API.
	 * If available the URL of the LARGE version of thumbnails is returned, otherwise the first available   
	 * 
	 * If <code>limit > DEFAULT_BLOCKSIZE</code>, the thumbnails will be be fetched iteratively in DEFAULT_BLOCKSIZE chuncks
	 * 
	 * @param start - first position in results. if smaller than 0, this parameter will default to 1
	 * @param limit - the number or returned results. If <code>start + limit > totalResults</code>, the (last) available result starting with the start position will be returned
	 * @return
	 * @throws EuropeanaApiProblem 
	 */
	public Map<String, String> getThumbnailsForCollection(int thumbnailSizeCode, int start, int limit, int errorHandlingPolicy) 
			throws TechnicalRuntimeException, IOException, EuropeanaApiProblem {
		return getContentMap(thumbnailSizeCode, start, limit, errorHandlingPolicy);
	}
	
	
}
