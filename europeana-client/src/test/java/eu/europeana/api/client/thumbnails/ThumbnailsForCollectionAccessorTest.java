package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import eu.europeana.api.client.exception.EuropeanaApiProblem;

public class ThumbnailsForCollectionAccessorTest extends ThumbnailAccessorUtils{

	@Test
	public void testGetThumbnailsForCollectionLimit() throws IOException, EuropeanaApiProblem {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				TEST_COLLECTION_NAME);
		//201 = 2* 100 +1  
		int resultsSize = (2 * ThumbnailsAccessor.DEFAULT_BLOCKSIZE) + 1;
		//start at second page
		int startPosition = 0; 
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(startPosition,
				resultsSize, ThumbnailsAccessor.ERROR_POLICY_RETHROW);
		
		
		// assert all image urls are correct
		assertTrue(thumbnails.size() == resultsSize);
		
		// check if the contnt of the collection was changed 
		assertTrue(TEST_COLLECTION_SIZE == tca.totalResults);
				
		
	}

	@Test
	public void testGetThumbnailsForCollectionAll() throws IOException, EuropeanaApiProblem {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				DEVELOPMENT_COLLECTION_NAME);
		int resultsSize = -1;
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(0,
				resultsSize, ThumbnailsAccessor.ERROR_POLICY_RETHROW);
		
		int objectsInDevCollection = 4;
		int objectsWithThumbnailInDevCollection = 2;
		
		// check total 
		assertTrue(tca.totalResults == objectsInDevCollection);
				
		// check correct thumbnail extraction
		assertTrue(thumbnails.size() == objectsWithThumbnailInDevCollection);
				
//		String fileName = "/collection_07501_thumbnails.csv";
//		writeThumbnailsToCsvFile(thumbnails, fileName);
	}

	
	//@Test
	public void createEvaluationDataset() throws IOException {
		// int objects = buildImageSet("Rijksmuseum-portrets",
		// "90402_M_NL_Rijksmuseum", "portret", "schilderij");
		// assertEquals(objects, 1243) ;
	}

}
