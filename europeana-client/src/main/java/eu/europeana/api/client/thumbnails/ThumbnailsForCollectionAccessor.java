package eu.europeana.api.client.thumbnails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.europeana.api.client.Api2Query;
import eu.europeana.api.client.Api2QueryInterface;
import eu.europeana.api.client.connection.EuropeanaApi2Client;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;

public class ThumbnailsForCollectionAccessor extends ThumbnailsAccessor{

	private static final Log logger = LogFactory.getLog(ThumbnailsForCollectionAccessor.class);
	//this should be the same as the maximum of results by the invocation of the Europeana API
	public static final int DEFAULT_BLOCKSIZE = 100; 
	//private String collectionName;
	private int blockSize = DEFAULT_BLOCKSIZE;
	Map <String, String> res;
	
	private Api2QueryInterface query;
	long totalResults = -1; 
	
	public ThumbnailsForCollectionAccessor(String collectionName){
		this(collectionName, null);
	}
		
	
	public ThumbnailsForCollectionAccessor(String collectionName, EuropeanaApi2Client apiClient){
		
		this(new Api2Query(collectionName), apiClient);
	}


	public ThumbnailsForCollectionAccessor(Api2QueryInterface query,
			EuropeanaApi2Client apiClient) {
		
		if(apiClient != null)
			europeanaClient = apiClient;
		else
			europeanaClient = new EuropeanaApi2Client();
		
		setQuery(query);
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
	 * @throws EuropeanaApiProblem 
	 */
	public Map<String, String> getThumbnailsForCollection(int start, int limit, int errorHandlingPolicy) throws TechnicalRuntimeException{
		//if no limit set, search Integer.MAX_VALUE
		int lastItemPosition;
		
		if(limit < 0)
			limit = Integer.MAX_VALUE / 2;
		
		lastItemPosition = start + limit -1;
		
		int blockStartPosition = start;
		
		//first position is 1 in the search API
		if(start <= 0){
			blockStartPosition = 1;
			lastItemPosition++;
		}
		
		//if one block
			if(limit <= getBlockSize())
				//TODO: move it o while to simplify code
				fetchBlock(blockStartPosition, limit, errorHandlingPolicy); 
			else{
				int blockLimit;
				//iteratively fetch results
				while(totalResults < 0 || blockStartPosition <= Math.min(totalResults, lastItemPosition)){ 
					blockLimit = Math.min(getBlockSize(), (lastItemPosition - blockStartPosition +1));
					
					fetchBlock(blockStartPosition,
							blockLimit, errorHandlingPolicy);
					//move to next block
					blockStartPosition += getBlockSize();
				} 
			}
			
		return res;
	}


	protected void fetchBlock(int blockStartPosition,
			int blockLimit, int errorHandlingPolicy) {
		try{
			fetchNextBlock(blockStartPosition, blockLimit);
			
		}catch (EuropeanaApiProblem e) {
			if(ERROR_POLICY_RETHROW == errorHandlingPolicy)
				throw new TechnicalRuntimeException("Api invokation error!",  e);
			else if(ERROR_POLICY_IGNORE == errorHandlingPolicy)
				logger.trace("Server Error occured and ignored: " + e.getMessage());
			else if(ERROR_POLICY_CONTINUE == errorHandlingPolicy)
				logger.warn("Server Error occured. The list of search results is incomplete: " + e.getMessage());
		}catch(RuntimeException e){
			if(ERROR_POLICY_RETHROW == errorHandlingPolicy)
				throw new TechnicalRuntimeException("Api invokation error!",  e);
			else if(ERROR_POLICY_IGNORE == errorHandlingPolicy)
				logger.trace("Server Error occured and ignored: " + e.getMessage());
			else if(ERROR_POLICY_CONTINUE == errorHandlingPolicy)
				logger.warn("Server Error occured. The list of search results is incomplete: " + e.getMessage());
			
		}
	}
	
	protected void fetchNextBlock(int start, int limit) throws TechnicalRuntimeException, EuropeanaApiProblem{
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
		if(largestThumbnail == null)
			System.out.println("No thumbnail found!");
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

	public void setQuery(Api2QueryInterface query) {
		this.query = query;
	}

	public Api2QueryInterface getQuery() {
		return query;
	}
	
}
