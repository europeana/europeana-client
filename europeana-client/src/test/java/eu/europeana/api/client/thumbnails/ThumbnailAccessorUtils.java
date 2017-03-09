package eu.europeana.api.client.thumbnails;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

public class ThumbnailAccessorUtils extends EuClientDatasetUtil {

	protected static final String TEST_COLLECTION_NAME = "07501_*";
	protected static final int TEST_COLLECTION_SIZE = 40811;
	protected static final String DEVELOPMENT_COLLECTION_NAME = "00000_*";

	Api2QueryBuilder queryBuilder = new Api2QueryBuilder();

	public Api2QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	protected void writeThumbnailsToCsvFile(DatasetDescriptor dataset,
			Map<String, String> thumbnails, File file) throws IOException {

		writeThumbnailsToCsvFile(dataset, thumbnails, file,
				POLICY_OVERWRITE_FILE);
	}

	protected void writeThumbnailsToCsvFile(DatasetDescriptor dataset,
			Map<String, String> thumbnails, File file, int fileWritePolicy)
			throws IOException {

		writeMapToCsvFile(dataset, thumbnails, file, fileWritePolicy);
	}

	public int buildImageSet(DatasetDescriptor dataset, Api2QueryInterface query)
			throws IOException, EuropeanaApiProblem {

		return buildImageSet(dataset, query, 0, -1,
				ThumbnailsAccessor.ERROR_POLICY_RETHROW);

	}

	protected int buildImageSet(DatasetDescriptor dataset,
			Api2QueryInterface query, int start, int limit,
			int errorHandlingPolicy) throws IOException, EuropeanaApiProblem {

		File cvsFile = getCollectionCsvFile(dataset);

		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				query, null);
		tca.setQuery(query);

		int resultsSize = -1;
		if (limit > 0)
			resultsSize = limit;

		Map<String, String> thumbnails = null;
		try {
			thumbnails = tca.getThumbnailsForCollection(start, resultsSize,
					errorHandlingPolicy);
		} catch (TechnicalRuntimeException e) {
			System.out.println("error: " + e);
			System.out.println(tca.getQuery().toString());
			System.out.println(tca.getResults().size());
		}

		if (thumbnails != null) {
			writeThumbnailsToCsvFile(dataset, thumbnails, cvsFile);
		}

		// assert all image urls are correct
		// if (limit > 0)
		// assertEquals(limit, thumbnails.size());
		// else
		// assertEquals(tca.totalResults, thumbnails.size());

		return thumbnails.size();
	}

	protected Map<String, String> readThumbnailsMap(File thumbnailsCsvFile)
			throws FileNotFoundException, IOException {

		Map<String, String> thumbnailMap = null;
		if (blockSize > 0)
			thumbnailMap = new LinkedHashMap<String, String>(blockSize);
		else
			thumbnailMap = new LinkedHashMap<String, String>(
					ThumbnailsAccessor.DEFAULT_BLOCKSIZE);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(thumbnailsCsvFile));
			String line = null;
			String idAndThumbnail[];
			while ((line = reader.readLine()) != null) {
				// ignore comments
				if (line.startsWith("#"))
					continue;

				idAndThumbnail = line.split(";", 2);
				thumbnailMap.put(idAndThumbnail[0], idAndThumbnail[1]);
			}

			// process last incomplete block
		} finally {
			if (reader != null)
				reader.close();
		}

		return thumbnailMap;
	}

	protected void closeReader(BufferedReader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (Exception e) {
			log.warn("cannot close reader" + e);
		}
	}

	protected int createSubset(String subsetName, String collectionName,
			String portalUrl, int start, int expectedResults)
			throws MalformedURLException, UnsupportedEncodingException,
			IOException, EuropeanaApiProblem {

		DatasetDescriptor dataset = new DatasetDescriptor(subsetName,
				collectionName);
		Api2QueryInterface query = getQueryBuilder().buildQuery(portalUrl);
		log.info("Create subset with query: " + portalUrl);
		int objects = buildImageSet(dataset, query, start, -1,
				ThumbnailsAccessor.ERROR_POLICY_CONTINUE);
		// assertEquals(expectedResults, objects);
		return expectedResults - objects;
	}
	
	
//	protected void copyDatasetImages(File imageFolder, File destination) throws FileNotFoundException, IOException{
//		Map<String, String> datasetMap = readThumbnailsMap(getDataSetFile(false));
//		Set<String> ids = datasetMap.keySet();
//		File imageSrcFile;
//		File imageDestFile;
//		
//		for (String id : ids) {
//			imageSrcFile = new File(imageFolder, id + ".jpg");
//			imageDestFile = new File(destination, id + ".jpg");
//			copyFile(imageSrcFile, imageDestFile);
//		}
//	}
	
	public void copyThumbnails() throws FileNotFoundException, IOException{
		//setDataset("culturecam");
		File datasetFile = getDataSetFile(false);
		Map<String, String> thumbnailMap = readThumbnailsMap(datasetFile);
		File imageFile;
		File destFile = null;
		int cnt = 0;
		for (String id : thumbnailMap.keySet()) {
			System.out.println("copying image with id: " + id);
			imageFile = getImageFile(id);
			if(!imageFile.exists())
				System.out.println("Error: file not found + " + imageFile.getCanonicalPath());
			else{
				destFile = new File(imageFile.getPath().replaceFirst("app", "inst"));
				copyFile(imageFile, destFile);
				cnt++;
			}
				
		}
		String parentFolder = null;
		if(destFile != null)
			parentFolder = destFile.getParentFile().getParentFile().getPath();
		
		System.out.println("Successfully copied: " + cnt + " files to folder: " + parentFolder);
	}

	protected File getImageFile(String id){
		throw new RuntimeException(new OperationNotSupportedException("getImageFile(): method must be implemented in subclasses!")); 
	}

}
