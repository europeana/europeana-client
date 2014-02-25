package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import eu.europeana.api.client.Api2QueryInterface;
import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.TechnicalRuntimeException;

public class ThumbnailsForCollectionAccessorTest extends ThumbnailAccessorUtils{

	protected static final String TEST_COLLECTION_NAME = "07501_*";
	protected static final int TEST_COLLECTION_SIZE = 40811;
	protected static final String DEVELOPMENT_COLLECTION_NAME = "00000_*";
	
	@Test
	public void testGetThumbnailsForCollectionLimit() throws IOException, EuropeanaApiProblem {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				TEST_COLLECTION_NAME);
		//201 = 2* 100 +1  
		int resultsSize = (2 * ThumbnailsForCollectionAccessor.DEFAULT_BLOCKSIZE) + 1;
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

	
	public int buildImageSet(DatasetDescriptor dataset, Api2QueryInterface query) throws IOException {
		
		return buildImageSet(dataset,
				query,  0, -1, ThumbnailsAccessor.ERROR_POLICY_RETHROW);
		
	}

	protected int buildImageSet(DatasetDescriptor dataset, Api2QueryInterface query, int start,
			int limit, int errorHandlingPolicy) throws IOException {
		
//		Api2QueryInterface query = getQueryBuilder().buildQuery(dataset, generalTerms, what,
//				creator, objectType, provider, dataProvider, refinements);
		
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(query, null);
		tca.setQuery(query);
		
		int resultsSize = -1;
		if(limit > 0)
			resultsSize = limit;
		
		Map<String, String> thumbnails = null; 
		try{
			thumbnails = tca.getThumbnailsForCollection(start,
				resultsSize, errorHandlingPolicy);
		}catch(TechnicalRuntimeException e){
			System.out.println("error: " + e);
			System.out.println(tca.getQuery().toString());
			System.out.println(tca.res.size());
		}

		if(thumbnails != null){
			File cvsFile = getCollectionCsvFile(dataset);
			writeThumbnailsToCsvFile(dataset, thumbnails, cvsFile);
		}
		
		// assert all image urls are correct
		if(limit > 0)
			assertEquals(limit, thumbnails.size());
		else
			assertEquals(tca.totalResults, thumbnails.size());
		
		return thumbnails.size();
	}

	//@Test
	public void createEvaluationDataset() throws IOException {
		// int objects = buildImageSet("Rijksmuseum-portrets",
		// "90402_M_NL_Rijksmuseum", "portret", "schilderij");
		// assertEquals(objects, 1243) ;
	}

}
