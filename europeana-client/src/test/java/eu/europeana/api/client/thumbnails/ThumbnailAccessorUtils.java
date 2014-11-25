package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import eu.europeana.api.client.Api2QueryBuilder;
import eu.europeana.api.client.Api2QueryInterface;
import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.TechnicalRuntimeException;

public class ThumbnailAccessorUtils extends EuClientDatasetUtil {

	Logger log = Logger.getLogger(getClass());
	
	protected static final String TEST_COLLECTION_NAME = "07501_*";
	protected static final int TEST_COLLECTION_SIZE = 40811;
	protected static final String DEVELOPMENT_COLLECTION_NAME = "00000_*";
	final int DEFAULT_BLOCK_SIZE = 1000;
		
	Api2QueryBuilder queryBuilder = new Api2QueryBuilder();

	public Api2QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	protected void writeThumbnailsToCsvFile(DatasetDescriptor dataset,
			Map<String, String> thumbnails, File file) throws IOException {

		writeThumbnailsToCsvFile(dataset, thumbnails, file, false);
	}

	protected void writeThumbnailsToCsvFile(DatasetDescriptor dataset,
			Map<String, String> thumbnails, File file, boolean append)
			throws IOException {

		// create parent dirs
		file.getParentFile().mkdirs();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
		writeCvsFileHeader(writer, dataset.getImageSetName(), thumbnails,
				dataset.getClassifications());

		int count = 0;
		for (Entry<String, String> thumbnail : thumbnails.entrySet()) {

			writer.write(thumbnail.getKey());
			writer.write(";");
			writer.write(thumbnail.getValue());
			writer.write("\n");
			count++;
			if (count % 1000 == 0)
				writer.flush();
		}
		writer.flush();
		writer.close();
	}

	/**
	 * use {@link #writeThumbnailsToCsvFile(DatasetDescriptor, Map, File)}
	 * instead
	 * 
	 * @param imageSet
	 * @param thumbnails
	 * @param classifications
	 * @param file
	 * @throws IOException
	 */
	@Deprecated
	private void writeThumbnailsToCsvFile(String imageSet,
			Map<String, String> thumbnails, String[] classifications, File file)
			throws IOException {

		// create parent dirs
		file.getParentFile().mkdirs();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writeCvsFileHeader(writer, imageSet, thumbnails, classifications);

		int count = 0;
		for (Entry<String, String> thumbnail : thumbnails.entrySet()) {

			writer.write(thumbnail.getKey());
			writer.write(";");
			writer.write(thumbnail.getValue());
			writer.write("\n");
			count++;
			if (count % 1000 == 0)
				writer.flush();
		}
		writer.flush();
		writer.close();
	}

	protected void writeCvsFileHeader(BufferedWriter writer, String imageSet,
			Map<String, String> thumbnails, String[] classifications)
			throws IOException {

		writer.write("#");
		writer.write(imageSet);
		writer.write("; ");
		writer.write("" + thumbnails.size());
		writer.write("; ");
		if (classifications != null) {
			for (int i = 0; i < classifications.length; i++) {
				writer.write(classifications[i]);
				writer.write("; ");
			}
		}
		writer.write("\n");

	}

	/**
	 * use {@link #getCollectionCsvFile(DatasetDescriptor)} instead
	 */
	@Deprecated
	private File getCollectionCsvFile(String imageSet, String collectionName) {
		String fileName = getCollectionsCvsFolder() + imageSet + "_"
				+ encode(collectionName) + ".csv";
		return new File(fileName);
	}

	protected File getCollectionCsvFile(DatasetDescriptor dataset) {
		String fileName = getCollectionsCvsFolder() + dataset.getImageSetName()
				+ "_" + encode(dataset.getCollectionName()) + ".csv";
		return new File(fileName);
	}

	protected String getCollectionsCvsFolder() {
		return getConfiguration().getBaseFolder() + "/collections";
	}

	private String encode(String collectionName) {

		if (collectionName == null)
			return null;

		return collectionName.replace('*', 'X');

	}

	public int buildImageSet(DatasetDescriptor dataset, Api2QueryInterface query)
			throws IOException {

		return buildImageSet(dataset, query, 0, -1,
				ThumbnailsAccessor.ERROR_POLICY_RETHROW);

	}

	protected int buildImageSet(DatasetDescriptor dataset,
			Api2QueryInterface query, int start, int limit,
			int errorHandlingPolicy) throws IOException {

		// Api2QueryInterface query = getQueryBuilder().buildQuery(dataset,
		// generalTerms, what,
		// creator, objectType, provider, dataProvider, refinements);

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
			System.out.println(tca.res.size());
		}

		if (thumbnails != null) {
			File cvsFile = getCollectionCsvFile(dataset);
			writeThumbnailsToCsvFile(dataset, thumbnails, cvsFile);
		}

		// assert all image urls are correct
		if (limit > 0)
			assertEquals(limit, thumbnails.size());
		else
			assertEquals(tca.totalResults, thumbnails.size());

		return thumbnails.size();
	}
	
	protected Map<String, String> readThumbnailsMap(File thumbnailsCsvFile)
			throws FileNotFoundException, IOException {
		
		HashMap<String, String> thumbnailMap = null;
		if(blockSize > 0)
			thumbnailMap = new HashMap<String, String>(blockSize);
		else thumbnailMap = new HashMap<String, String>(ThumbnailsAccessor.DEFAULT_BLOCKSIZE);
		
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(thumbnailsCsvFile));
			String line = null;
			String idAndThumbnail[];
			while ((line = reader.readLine()) != null) {
				//ignore comments
				if(line.startsWith("#"))
					continue;
				
				idAndThumbnail = line.split(";", 2);
				thumbnailMap.put(idAndThumbnail[0], idAndThumbnail[0]); 
			}
			
			//process last incomplete block
		} finally {
				if(reader!=null)
					reader.close();
		}
		
		return thumbnailMap;
	}

	protected void closeReader(BufferedReader reader) {
		try{
			if(reader != null)
				reader.close();
		} catch (Exception e){
			log.warn("cannot close reader" + e); 
		}
	}
}
