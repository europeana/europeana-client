package eu.europeana.api.client.thumbnails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.europeana.api.client.Api2Query;
import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.EuropeanaApi2Item;
import eu.europeana.api.client.EuropeanaApi2Results;
import eu.europeana.api.client.exception.TechnicalRuntimeException;

public class ThumbnailsForCollectionAccessor extends ThumbnailsAccessor{

	private static final Log logger = LogFactory.getLog(ThumbnailsForCollectionAccessor.class);
	//this should be the same as the maximum of results by the invocation of the Europeana API
	public static final int DEFAULT_BLOCKSIZE = 100; 
	//private String collectionName;
	private int blockSize = DEFAULT_BLOCKSIZE;
	Map <String, String> res;
	
	private Api2Query query;
	long totalResults = -1; 
	
	public ThumbnailsForCollectionAccessor(String collectionName){
		this(collectionName, null);
	}
		
	
	public ThumbnailsForCollectionAccessor(String collectionName, EuropeanaApi2Client apiClient){
		if(apiClient != null)
			europeanaClient = apiClient;
		else
			europeanaClient = new EuropeanaApi2Client();
		
		setQuery(new Api2Query(collectionName));
		//setBlockSize(DEFAULT_BLOCKSIZE);
		res = new HashMap<String, String>(getBlockSize());
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
	 */
	public Map<String, String> getThumbnailsForCollection(int start, int limit){
		//if no limit set, search Integer.MAX_VALUE
		if(limit < 0)
			limit = Integer.MAX_VALUE;
		
		int blockStartPosition = start;
		int lastItemPosition = start + limit -1;
		
		//first position is 1 in the search API
		if(start <= 0){
			blockStartPosition = 1;
			lastItemPosition++;
		}
		
		//if one block
		if(limit <= getBlockSize())
			fetchNextBlock(blockStartPosition, limit); 
		else{
			int blockLimit;
			//iteratively fetch results
			while(totalResults < 0 || blockStartPosition <= Math.min(totalResults, lastItemPosition)){ 
				blockLimit = Math.min(getBlockSize(), (lastItemPosition - blockStartPosition +1));
				fetchNextBlock(blockStartPosition, blockLimit);
				//update next block start
				blockStartPosition += getBlockSize();
			} 
		} 
		
		return res;
	}
	
	protected void fetchNextBlock(int start, int limit){
		int noThumbnailCount = 0;
		
		try {
			EuropeanaApi2Results searchResults = europeanaClient.searchApi2(getQuery(), limit, start);
 			if(totalResults < 0)
				totalResults = searchResults.getTotalResults();
			//else .. we could use defensive programming and expect the same number of total results after each query
				
			for (EuropeanaApi2Item item : searchResults.getAllItems()) {
				if(item.getEdmPreview() != null && !item.getEdmPreview().isEmpty()){
					res.put(item.getId(), getLargestThumbnail(item));
					//logger(item.getId());
				}
				else
					noThumbnailCount++;
			}
			
			logger.info("Thumbnail URLS fethced : " + res.size());	
			logger.info("Items without Thumbnail: " + noThumbnailCount);	
			
		
		} catch (IOException e) {
			throw new TechnicalRuntimeException("Cannot fetch search results!", e);
		}
	}

	private String getLargestThumbnail(EuropeanaApi2Item item) {
		String largestThumbnail = item.getEdmPreview().get(0);
		//the following code is not needed, the API returns already the LARGE 
//		for (String url : item.getEdmPreview()) {
//			if(url.indexOf("size=LARGE") > 0){
//				largestThumbnail = url;
//				break;//the large version of the thumbnail was found
//			}
//		}
		return largestThumbnail;
	}
	
	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public void setQuery(Api2Query query) {
		this.query = query;
	}

	public Api2Query getQuery() {
		return query;
	}
	
}
