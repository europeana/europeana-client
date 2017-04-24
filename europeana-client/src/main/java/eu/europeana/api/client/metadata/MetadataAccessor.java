package eu.europeana.api.client.metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.config.EuropeanaApiConfiguration;
import eu.europeana.api.client.config.ThumbnailAccessConfiguration;
import eu.europeana.api.client.connection.HttpConnector;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.model.search.EuropeanaApi2Item;
import eu.europeana.api.client.search.query.Api2QueryInterface;
import eu.europeana.api.client.thumbnails.ThumbnailsAccessor;

public class MetadataAccessor {

	protected static final Log log = LogFactory.getLog(ThumbnailsAccessor.class);
	protected HttpConnector http = new HttpConnector();
	protected EuropeanaApi2Client europeanaClient;
	private boolean skipExistingFiles = true;
	private int metadataItems = 0;
	private int savedFiles = 0;
	private int savedBlockFiles = 0;
	private int skippedItems = 0;
	private boolean storeItemsAsJson = false;
	private boolean storeBlockwiseAsJson = false;
	private int errorHandlingPolicy = ERROR_POLICY_RETHROW;
	
	public static final int DEFAULT_BLOCKSIZE = 100;
	protected int blockSize = DEFAULT_BLOCKSIZE;
	public static int ERROR_POLICY_RETHROW = 1;
	public static int ERROR_POLICY_STOP = 5;
	public static int ERROR_POLICY_IGNORE = 9;
	public static int ERROR_POLICY_CONTINUE = 99;

	// this should be the same as the maximum of results by the invocation of
	// the Europeana API
	protected Map<String, String> results;

	protected Api2QueryInterface query;
	protected long totalResults = -1;

	public MetadataAccessor() {}
	
	public MetadataAccessor(Api2QueryInterface query, EuropeanaApi2Client apiClient) {

		if (apiClient != null)
			this.europeanaClient = apiClient;
		else
			this.europeanaClient = new EuropeanaApi2Client();

		reset(query);
	}

	void reset(Api2QueryInterface query) {
		setQuery(query);
		// setBlockSize(DEFAULT_BLOCKSIZE);
		results = new LinkedHashMap<String, String>(getBlockSize());
		totalResults = -1;
	}

	protected EuropeanaApi2Client getEuropeanaClient() {
		return europeanaClient;
	}

	protected boolean isSkipExistingFiles() {
		return skipExistingFiles;
	}

	public void setSkipExistingFiles(boolean skipExistingFiles) {
		this.skipExistingFiles = skipExistingFiles;
	}

	public int getSkippedItems() {
		return skippedItems;
	}

	protected void incrementSkippedItems() {
		this.skippedItems++;
	}

	protected void resetSkippedItems() {
		this.skippedItems = 0;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * Method to set the query.
	 * 
	 * @param query:
	 *            Api2QueryInterface object representing the query to be set.
	 */
	public void setQuery(Api2QueryInterface query) {
		this.query = query;
	}

	/**
	 * Method to retrieve the query.
	 * 
	 * @return Api2QueryInterface object representing the retrieved query.
	 */
	public Api2QueryInterface getQuery() {
		return query;
	}

	public Map<String, String> getResults() {
		return results;
	}

	public long getTotalResults() {
		return totalResults;
	}

	/**
	 * Helper method to fetch a block in a thumbnail.
	 * 
	 * @param blockStartPosition:
	 *            starting position for block fetching.
	 * @param blockLimit:
	 *            limit of blocks to be fetched.
	 * @param errorHandlingPolicy:
	 *            policy for error handling.
	 */
	protected void fetchBlock(int edmField, int blockStartPosition, int blockLimit) {
		try {
			fetchNextBlock(edmField, blockStartPosition, blockLimit);

		} catch (EuropeanaApiProblem e) {
			handleException(e);
		} catch (RuntimeException e) {
			handleException(e);
		}
	}

	void handleException(Throwable e) {
		if (ERROR_POLICY_RETHROW == errorHandlingPolicy)
			throw new TechnicalRuntimeException("Api invokation error!", e);
		else if (ERROR_POLICY_IGNORE == errorHandlingPolicy)
			log.trace("Server Error occured and ignored: " + e.getMessage());
		else if (ERROR_POLICY_CONTINUE == errorHandlingPolicy)
			log.warn("Server Error occured. The list of search results is incomplete: " + e.getMessage());
	}

	/**
	 * Helper method to fetch the next block in a thumbnail.
	 * 
	 * @param start:
	 *            starting position for block fetching.
	 * @param limit:
	 *            limit of blocks to be fetched.
	 */
	protected void fetchNextBlock(int edmField, int start, int limit)
			throws TechnicalRuntimeException, EuropeanaApiProblem {
		int noContentCount = 0;

		try {
			EuropeanaApi2Results searchResults = europeanaClient.searchApi2(getQuery(), limit, start);

			if (totalResults < 0)
				totalResults = searchResults.getTotalResults();
			// else .. we could use defensive programming and expect the same
			// number of total results after each query
			if (isStoreBlockwiseAsJson() && getQuery().getCollectionName() != null)
				storeResultsBlockInJsnFile(searchResults, start, getQuery().getCollectionName());

			for (EuropeanaApi2Item item : searchResults.getAllItems()) {
				if (isStoreItemsAsJson())
					storeItemsInJsonFile(item);

				if (isReturnContentMap(edmField))
					noContentCount = addContentFieldToMap(item, edmField, noContentCount);

				metadataItems++;
			}

			log.info("Metadata Items fetched : " + metadataItems);
			if (isStoreItemsAsJson())
				log.trace("New saved metadata files (json): " + savedFiles);
			if (edmField > 0)
				log.trace("Items with empty field value: " + noContentCount);

		} catch (IOException e) {
			throw new TechnicalRuntimeException("Cannot fetch search results!", e);
		}
	}

	protected void storeResultsBlockInJsnFile(EuropeanaApi2Results searchResults, int start, String collectionName) {

		//
		String collectionNumber = collectionName.split("_", 2)[0];

		String metadataFolder = getConfiguration().getMetadataFolder();
		String filePath = metadataFolder + collectionNumber + "_blocks/offset_" + start + ".json";
		File jsonFile = new File(filePath);
		if (jsonFile.exists() && skipExistingFiles)
			log.trace("Skip existing file: " + jsonFile.getAbsolutePath());
		else {
			try {
				FileUtils.writeStringToFile(jsonFile, searchResults.toJSON(), "UTF-8");
				savedBlockFiles++;
				log.debug("Stored chunk : " + start + " for collection: " + collectionNumber);
				log.trace("New File Stored locally: " + jsonFile.getAbsolutePath());
			} catch (IOException e) {
				handleException(e);
			}
		}

	}

	int addContentFieldToMap(EuropeanaApi2Item item, int edmField, int noContentCount) {
		String contentField = getFieldContent(item, edmField);
		if (contentField != null && !contentField.isEmpty())
			results.put(item.getId(), contentField);
		else
			noContentCount++;
		return noContentCount;
	}

	boolean isReturnContentMap(int edmField) {
		return edmField > 0;
	}

	protected void storeItemsInJsonFile(EuropeanaApi2Item item) {
		//
		String id = item.getId();
		String metadataFolder = getConfiguration().getMetadataFolder();
		String filePath = getConfiguration().getJsonMetadataFile(id, metadataFolder, 
				EuropeanaApiConfiguration.REPRESENTATION_PREVIEW);
		File jsonFile = new File(filePath);
		if (jsonFile.exists() && skipExistingFiles)
			log.trace("Skip existing file: " + jsonFile.getAbsolutePath());
		else {
			try {
				FileUtils.writeStringToFile(jsonFile, item.toJSON());
				savedFiles++;
			} catch (IOException e) {
				handleException(e);
			}
		}

	}
	
	public File getDatasetFile(String fileName) {
		
		if (fileName == null || fileName.length() == 0) 
			fileName = "overview.csv";
		String metadataFolder = getConfiguration().getMetadataFolder();
		File datasetFile = new File(metadataFolder, fileName);
		return datasetFile;
	}

	
	

	protected EuropeanaApiConfiguration getConfiguration() {
		return ClientConfiguration.getInstance();
	}

	protected String getFieldContent(CommonMetadata item, int edmField) {
		return item.getFieldContent(edmField);
	}

	public boolean isStoreItemsAsJson() {
		return storeItemsAsJson;
	}

	public void setStoreItemsAsJson(boolean storeItemsAsJson) {
		this.storeItemsAsJson = storeItemsAsJson;
	}

	/**
	 * This method extracts the map of <thumbnailId, thumbnailURL> by invoking
	 * the search API. If available the URL of the LARGE version of thumbnails
	 * is returned, otherwise the first available
	 * 
	 * If <code>limit > DEFAULT_BLOCKSIZE</code>, the thumbnails will be be
	 * fetched iteratively in DEFAULT_BLOCKSIZE chuncks
	 * 
	 * @param start
	 *            - first position in results. if smaller than 0, this parameter
	 *            will default to 1
	 * @param limit
	 *            - the number or returned results. If
	 *            <code>start + limit > totalResults</code>, the (last)
	 *            available result starting with the start position will be
	 *            returned
	 * @return
	 * @throws IOException
	 * @throws EuropeanaApiProblem
	 */
	public Map<String, String> getContentMap(int edmField, int start, int limit, int errorHandlingPolicy)
			throws TechnicalRuntimeException, IOException, EuropeanaApiProblem {

		// boolean useCursor = false;
		// first read the number of total results
		// if(start + limit <= 1000)
		// return getContentMapBasicPagination(edmField, start, limit,
		// errorHandlingPolicy);

		EuropeanaApi2Results searchResults = europeanaClient.searchApi2(getQuery(), 0, start);
		if (searchResults.getTotalResults() <= 1000)
			return getContentMapBasicPagination(edmField, start, limit, errorHandlingPolicy);
		else
			return getContentMapCursorPagination(edmField, start, limit, (int) searchResults.getTotalResults(),
					errorHandlingPolicy);
	}

	/**
	 * @param edmField
	 * @param start
	 * @param limit
	 * @param totalResults
	 * @param errorHandlingPolicy
	 * @return
	 * @throws IOException
	 * @throws EuropeanaApiProblem
	 */
	private Map<String, String> getContentMapCursorPagination(int edmField, int start, int limit, int totalResults,
			int errorHandlingPolicy) throws IOException, EuropeanaApiProblem {

		this.errorHandlingPolicy = errorHandlingPolicy;

		int noContentCount = 0;
		int metadataItems = 0;

		String cursor = "*";
		EuropeanaApi2Results resultsBlock;
		int iteration = 0;
		int blockStart = 0;
		if(limit < 0)
			limit = totalResults;
		do {

			try {

				int rows = getBlockSize();
				//COMPUTE SIZE FOR THE LAST PAGE
				if (limit < metadataItems + rows) {
					rows = Math.abs(metadataItems - limit);
				}
				resultsBlock = europeanaClient.searchApi2(getQuery(), cursor, rows);
				blockStart = iteration * getBlockSize();

				if (isStoreBlockwiseAsJson() && getQuery().getCollectionName() != null)
					storeResultsBlockInJsnFile(resultsBlock, blockStart, getQuery().getCollectionName());

				for (EuropeanaApi2Item item : resultsBlock.getAllItems()) {
					if (isStoreItemsAsJson())
						storeItemsInJsonFile(item);

					if (isReturnContentMap(edmField))
						noContentCount = addContentFieldToMap(item, edmField, noContentCount);

					metadataItems++;
				}

				log.info("Metadata Items fetched : " + metadataItems);
				if (isStoreItemsAsJson())
					log.trace("New saved metadata files (json): " + savedFiles);
				if (edmField > 0)
					log.trace("Items with empty field value: " + noContentCount);

				cursor = URLEncoder.encode(resultsBlock.getNextCursor(), "UTF-8");
				log.trace("cursor:" + cursor);
				iteration++;
			} catch (Throwable th) {
				handleException(th);
			} 
			
		} while (cursor != null && ((limit > metadataItems)) );

		return results;
	}

	private Map<String, String> getContentMapBasicPagination(int edmField, int start, int limit,
			int errorHandlingPolicy) throws IOException, EuropeanaApiProblem {

		this.errorHandlingPolicy = errorHandlingPolicy;
		// if no limit set, search Integer.MAX_VALUE
		int lastItemPosition;

		if (limit < 0)
			limit = Integer.MAX_VALUE / 2;

		lastItemPosition = start + limit - 1;

		int blockStartPosition = start;

		// first position is 1 in the search API
		if (start <= 0) {
			blockStartPosition = 1;
			lastItemPosition++;
		}

		// if one block
		if (limit <= getBlockSize())
			// TODO: move it o while to simplify code
			fetchBlock(edmField, blockStartPosition, limit);
		else {
			int blockLimit;
			// iteratively fetch results
			while (totalResults < 0 || blockStartPosition <= Math.min(totalResults, lastItemPosition)) {
				blockLimit = Math.min(getBlockSize(), (lastItemPosition - blockStartPosition + 1));

				fetchBlock(edmField, blockStartPosition, blockLimit);
				// move to next block
				blockStartPosition += getBlockSize();
			}
		}

		return results;
	}

	/**
	 * Helper method for the creation of a file output stream.
	 * 
	 * @param imageFolder
	 *            : folder where images are to be saved.
	 * @param id
	 *            : id of specific image file.
	 * @return FileOutputStream object prepared to store images.
	 * @throws FileNotFoundException
	 */
	protected FileOutputStream createOutputStream(File imageFile) throws FileNotFoundException {

		imageFile.getParentFile().mkdirs();
		return new FileOutputStream(imageFile);
	}

	protected int getSavedFiles() {
		return savedFiles;
	}

	protected boolean isStoreBlockwiseAsJson() {
		return storeBlockwiseAsJson;
	}

	protected void setStoreBlockwiseAsJson(boolean storeBlockwiseAsJson) {
		this.storeBlockwiseAsJson = storeBlockwiseAsJson;
	}

	protected int getSavedBlockFiles() {
		return savedBlockFiles;
	}

//	protected void setMetadataFolder(String metadataFolder) {
//		this.metadataFolder = metadataFolder;
//	}

}
