package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class ThumbnailsForCollectionAccessorTest{

	protected static final String TEST_COLLECTION_NAME = "07501_*";
	protected static final int TEST_COLLECTION_SIZE = 40811;
	protected static final String DEVELOPMENT_COLLECTION_NAME = "00000_*";

	@Test
	public void testGetThumbnailsForCollectionLimit() throws IOException {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				TEST_COLLECTION_NAME);
		//201 = 2* 100 +1  
		int resultsSize = (2 * ThumbnailsForCollectionAccessor.DEFAULT_BLOCKSIZE) + 1;
		//start at second page
		int startPosition = 0; 
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(startPosition,
				resultsSize);
		
		
		// assert all image urls are correct
		assertTrue(thumbnails.size() == resultsSize);
		
		// check if the contnt of the collection was changed 
		assertTrue(TEST_COLLECTION_SIZE == tca.totalResults);
				
		
	}

	@Test
	public void testGetThumbnailsForCollectionAll() throws IOException {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				DEVELOPMENT_COLLECTION_NAME);
		int resultsSize = -1;
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(0,
				resultsSize);
		
		int objectsInDevCollection = 4;
		int objectsWithThumbnailInDevCollection = 2;
		
		// check total 
		assertTrue(tca.totalResults == objectsInDevCollection);
				
		// check correct thumbnail extraction
		assertTrue(thumbnails.size() == objectsWithThumbnailInDevCollection);
				
//		String fileName = "/collection_07501_thumbnails.csv";
//		writeThumbnailsToCsvFile(thumbnails, fileName);
	}

	private void writeThumbnailsToCsvFile(String imageSet, Map<String, String> thumbnails,
			String[] classifications, File file) throws IOException {
		
		//create parent dirs
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

	private void writeCvsFileHeader(BufferedWriter writer, String imageSet, Map<String, String> thumbnails,
			String[] classifications) throws IOException {
		
		writer.write("#");
		writer.write(imageSet);
		writer.write("; ");
		writer.write("" + thumbnails.size());
		writer.write("; ");
		for (int i = 0; i < classifications.length; i++) {
			writer.write(classifications[i]);
			writer.write("; ");
		}
		writer.write("\n");
		
	}

	public int buildImageSet(String imageSet, String collectionName, String[] classifications,
			String generalTerms, String what) throws IOException {

		return buildImageSet(imageSet, collectionName, classifications, generalTerms, what, null);
	}

	public int buildImageSet(String imageSet, String collectionName, String[] classifications,
			String generalTerms, String what, String objectType) throws IOException {
		return buildImageSet(imageSet, collectionName, classifications, generalTerms, what, null, objectType); 
	}

		
	public int buildImageSet(String imageSet, String collectionName, String[] classifications,
			String generalTerms, String what, String creator, String objectType) throws IOException {
		
		return buildImageSet(imageSet, collectionName, classifications,
				generalTerms, what, creator, objectType, null);
	}

	public int buildImageSet(String imageSet, String collectionName, String[] classifications,
			String generalTerms, String what, String creator, String objectType, String provider) throws IOException {
		
		return buildImageSet(imageSet, collectionName, classifications,
				generalTerms, what, creator, objectType, provider, null);
	}
	

	public int buildImageSet(String imageSet, String collectionName, String[] classifications,
			String generalTerms, String what, String creator, String objectType, String provider, String[] refinements) throws IOException {
		
		return buildImageSet(imageSet, collectionName, classifications, 0, -1,
				generalTerms, what, creator, objectType, provider, refinements);
		
	}


	public int buildImageSet(String imageSet, String collectionName, String[] classifications, int start, int limit,
			String generalTerms, String what, String creator, String objectType, String provider, String[] refinements) throws IOException {
		
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				collectionName);
		tca.getQuery().setWhatTerms(what);
		tca.getQuery().setGeneralTerms(generalTerms);
		tca.getQuery().setCreator(creator);
		tca.getQuery().setType(objectType);
		tca.getQuery().setProvider(provider);
		
		if(refinements != null && refinements.length > 0){
			for (int i = 0; i < refinements.length; i++) {
				tca.getQuery().addQueryRefinement(refinements[i]);		
			}
		}
		
		int resultsSize = -1;
		if(limit > 0)
			resultsSize = limit;
		
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(start,
				resultsSize);

		File cvsFile = getCollectionCsvFile(imageSet, collectionName);
		writeThumbnailsToCsvFile(imageSet, thumbnails, classifications, cvsFile);

		// assert all image urls are correct
		if(limit > 0)
			assertEquals(limit, thumbnails.size());
		else
			assertEquals(tca.totalResults, thumbnails.size());
		
		return thumbnails.size();
	}

	File getCollectionCsvFile(String imageSet, String collectionName) {
		String fileName = getCollectionsCvsFolder() + imageSet + "_" + encode(collectionName) + ".csv";
		return new File(fileName);
	}

	protected String getCollectionsCvsFolder() {
		return "/";
	}

	
	private String encode(String collectionName) {
		return collectionName.replace('*', 'X');
	}

	//@Test
	public void createEvaluationDataset() throws IOException {
		// int objects = buildImageSet("Rijksmuseum-portrets",
		// "90402_M_NL_Rijksmuseum", "portret", "schilderij");
		// assertEquals(objects, 1243) ;
	}

}
